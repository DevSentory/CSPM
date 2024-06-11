package kyobo.cspm.service.descirbe_group;

import com.mysema.commons.lang.Pair;
import kyobo.cspm.describe.dto.DescribeAccount;
import kyobo.cspm.describe.entity.DescribeEntity;
import kyobo.cspm.describe.entity.ErrorLogEntity;
import kyobo.cspm.describe.repository.ErrorLogRepository;
import kyobo.cspm.enums.DescribeType;
import kyobo.cspm.enums.ResultType;
import kyobo.cspm.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class GroupHandler {

    private final VpcGroup vpcGroup;
    private final Ec2Group ec2Group;
    private final S3Group s3Group;
    private final ErrorLogRepository repoErrorLog;

    // 클라이언트 정보
    private DescribeAccount describeAccount;

    // 자원 스캔
    public CompletableFuture<Pair<Boolean, List<DescribeEntity>>> serviceGroupDescribe(DescribeAccount describeAccount) {
        this.describeAccount = describeAccount;

        // 각 그룹의 HashMap 초기화
        vpcGroup.getDescribeEntityList().clear();
        ec2Group.getDescribeEntityList().clear();
        s3Group.getDescribeEntityList().clear();

        List<Supplier<Pair<Boolean, List<DescribeEntity>>>> asyncTaskList = new ArrayList<>();
        for (DescribeType.Group group : DescribeType.Group.values()) {
            switch (group) {
                case VPC -> asyncTaskList.addAll(vpcDescribe());
                case EC2 -> asyncTaskList.addAll(ec2Describe());
                case S3 -> asyncTaskList.addAll(s3Describe());
            }
        }

        return asyncTaskRun(asyncTaskList);
    }

    // vpc 자원 스캔 목록 저장
    private List<Supplier<Pair<Boolean, List<DescribeEntity>>>> vpcDescribe() {
        return Arrays.asList(
                () -> safeDescribeCall(DescribeType.VPC, vpcGroup::vpcDescribe),
                () -> safeDescribeCall(DescribeType.SUBNET, vpcGroup::subnetDescribe),
                () -> safeDescribeCall(DescribeType.SECURITY_GROUP, vpcGroup::securityGroupDescribe),
                () -> safeDescribeCall(DescribeType.ROUTE, vpcGroup::routeDescribe),
                () -> safeDescribeCall(DescribeType.IGW, vpcGroup::igwDescribe));
    }

    // ec2 자원 스캔 목록 저장
    private List<Supplier<Pair<Boolean, List<DescribeEntity>>>> ec2Describe() {
        return Arrays.asList(
                () -> safeDescribeCall(DescribeType.INSTANCE, ec2Group::instanceDescribe),
                () -> safeDescribeCall(DescribeType.EBS, ec2Group::ebsDescribe),
                () -> safeDescribeCall(DescribeType.ENI, ec2Group::eniDescribe)
        );
    }

    // s3 자원 스캔 목록 저장
    private List<Supplier<Pair<Boolean, List<DescribeEntity>>>> s3Describe() {
        return List.of(
                () -> safeDescribeCall(DescribeType.S3, s3Group::bucketInfoDescribe)
        );
    }

    // 비동기 작업 목록 실행 및 대기
    // Java8 - Stream : 컬렉션, 배열 등에 저장되어 있는 요소들을 하나씩 참조하며 반복적인 처리를 가능하게 하는 기능
    private CompletableFuture<Pair<Boolean, List<DescribeEntity>>> asyncTaskRun(
            List<Supplier<Pair<Boolean, List<DescribeEntity>>>> tasks) {



        // 실행
        List<CompletableFuture<Pair<Boolean, List<DescribeEntity>>>> futureTaskList = tasks.stream()
                .map(CompletableFuture::supplyAsync).toList();

        return CompletableFuture.allOf(futureTaskList.toArray(new CompletableFuture[0]))
                .thenApply(_void -> {
                    boolean allSuccess = true;
                    List<DescribeEntity> resultList = new ArrayList<>();

                    for (CompletableFuture<Pair<Boolean, List<DescribeEntity>>> futureTask : futureTaskList) {
                        Pair<Boolean, List<DescribeEntity>> pairResult = futureTask.join();
                        allSuccess = allSuccess && pairResult.getFirst();

                        // 결과 목록에 성공 여부를 설정하고 추가
                        List<DescribeEntity> entityList = pairResult.getSecond();
                        entityList.forEach(entity -> entity.setSuccess(pairResult.getFirst()));
                        resultList.addAll(entityList);
                    }



                    return Pair.of(allSuccess, resultList);
                });
    }

    // 자원 스캔 실행
    public <T> Pair<Boolean, List<T>> safeDescribeCall(DescribeType describeType, Supplier<List<T>> describeCall) {
        boolean isSuccess = true;
        Pair<ResultType, String> exceptionPair;
        List<T> resultData = Collections.emptyList();
        try {
            resultData = describeCall.get();
        } catch (Ec2Exception | S3Exception e) {
            isSuccess = false;
            exceptionPair = checkEC2Exception(e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage());
            saveErrorLog(describeType, exceptionPair.getFirst(), exceptionPair.getSecond());
            e.fillInStackTrace();
        } catch (Exception e) {
            isSuccess = false;
            e.fillInStackTrace();
        }

        return new Pair<>(isSuccess, resultData);
    }

    // 자원 스캔 예외 처리
    private Pair<ResultType, String> checkEC2Exception(String errorCode, String errorMessage) {
        String exceptionMsg = "";
        ResultType resultType = switch (errorCode) {
            case "AuthFailure" -> {
                exceptionMsg = "when calling the DescribeSubnets operation: AWS was not able to validate the provided access credentials";
                yield ResultType.FAIL_AUTH_FAILURE;
            }
            case "AccessDenied" -> {
                exceptionMsg = "when calling the DescribeLoadBalancers operation: User: arn:aws:iam::339712736815:user/cspm_access " +
                        "is not authorized to perform: elasticloadbalancing:DescribeLoadBalancers because no identity-based policy " +
                        "allows the elasticloadbalancing:DescribeLoadBalancers action";
                yield ResultType.FAIL_ACCESS_DENIED;
            }
            case "AccessDeniedException" -> {
                exceptionMsg = "when calling the DescribeLimits operation: User: arn:aws:iam::339712736815:user/cspm_access " +
                        "is not authorized to perform: dynamodb:DescribeLimits on resource: * because no identity-based policy " +
                        "allows the dynamodb:DescribeLimits action";
                yield ResultType.FAIL_ACCESS_DENIED_EXCEPTION;
            }
            case "UnauthorizedOperation" -> {
                exceptionMsg = "when calling the DescribeVolumes operation: You are not authorized to perform this operation. " +
                        "User: arn:aws:iam::339712736815:user/cspm_access is not authorized to perform: " +
                        "ec2:DescribeVolumes because no identity-based policy allows the ec2:DescribeVolumes action";
                yield ResultType.FAIL_UNAUTHORIZED_OPERATION;
            }
            // 추가 예외 처리가 필요하면 case 추가해서 사용하세요 :)
            default -> {
                exceptionMsg = "AWS 서비스 예외: " + errorMessage;
                yield ResultType.FAIL_EXCEPTION;
            }
        };

        return Pair.of(resultType, exceptionMsg);
    }

    // 예외 에러 로그 추가
    private void saveErrorLog(DescribeType describeType, ResultType resultType, String exceptionMsg) {
        String describeTime = StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm");
        repoErrorLog.save(ErrorLogEntity.of(
                describeAccount.getAccountId(), describeAccount.getAccountName(), describeType.getGroup().name(), describeType.name(),
                describeTime, resultType.name(), exceptionMsg));
    }
}