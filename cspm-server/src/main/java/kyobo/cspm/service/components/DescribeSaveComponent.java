package kyobo.cspm.service.components;

import jakarta.annotation.PostConstruct;
import kyobo.cspm.describe.entity.describe.*;
import kyobo.cspm.describe.repository.*;
import kyobo.cspm.enums.DescribeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DescribeSaveComponent {

    private List<Runnable> saveTasks;
    private Map<DescribeType, List<?>> describeTypeListMap;

    private final VpcRepository vpcRepository;
    private final SubnetRepository subnetRepository;
    private final SecurityGroupRepository securityGroupRepository;
    private final RouteRepository routeRepository;
    private final IGWRepository igwRepository;
    private final InstanceRepository instanceRepository;
    private final EBSRepository ebsRepository;
    private final ENIRepository eniRepository;

    @PostConstruct
    public void init() {
        saveTasks = new ArrayList<>();
        describeTypeListMap = new Hashtable<>();
    }

    /**
     * task : 작업(Runnable) 리스트 저장
     * param <T> : 엔티티 리스트
     * param <R> : JpaRepository를 상속받는 리포지토리의 타입
     */
    public <T, R extends JpaRepository<T, String>> void saveAwsResources(DescribeType describeType, List<T> entities, R repository) {
        Runnable saveTask = () -> repository.saveAll(entities);
        saveTasks.add(saveTask);
        describeTypeListMap.put(describeType, entities);
    }

    // task : 데이터 저장
    public void saveAllAwsResources() {
        for (Runnable saveTask : saveTasks) {
            saveTask.run();
        }
        saveTasks.clear();
    }

    // task : 엔티티간 관계 초기화
    public void initializeEntityConnections() {
        UpdateSubnetEntityAssociations();
        updateRouteEntityAssociations();
        updateIGWEntityAssociations();
        updateInstanceEntityAssociations();
        updateEBSEntityAssociations();
        updateENIEntityAssociations();
    }

    private void UpdateSubnetEntityAssociations() {
        List<SubnetEntity> updateEntityList = new ArrayList<>();
        Optional.ofNullable(describeTypeListMap.get(DescribeType.SUBNET))
                .ifPresent(valueList -> valueList.stream()
                        // 타입 확인
                        .filter(SubnetEntity.class::isInstance)
                        .map(SubnetEntity.class::cast)
                        .forEach(subnet -> {
                            if (null != subnet.getVpcId()) {
                                vpcRepository.findById(subnet.getVpcId()).ifPresent(subnet::setVpcEntity);
                                updateEntityList.add(subnet);
                            }
                        })
                );

        // 업데이트
        subnetRepository.saveAll(updateEntityList);
    }

    private void updateRouteEntityAssociations() {
        List<RouteEntity> updateEntityList = new ArrayList<>();
        Optional.ofNullable(describeTypeListMap.get(DescribeType.ROUTE))
                .ifPresent(valueList -> valueList.stream()
                        .filter(RouteEntity.class::isInstance)
                        .map(RouteEntity.class::cast)
                        .forEach(route -> {
                            if (null != route.getVpcId()) {
                                vpcRepository.findById(route.getVpcId()).ifPresent(route::setVpcEntity);
                                updateEntityList.add(route);
                            }
                        })
                );

        routeRepository.saveAll(updateEntityList);
    }

    private void updateIGWEntityAssociations() {
        List<IGWEntity> updateEntityList = new ArrayList<>();
        Optional.ofNullable(describeTypeListMap.get(DescribeType.IGW))
                .ifPresent(valueList -> valueList.stream()
                        .filter(IGWEntity.class::isInstance)
                        .map(IGWEntity.class::cast)
                        .forEach(igw -> {
                            if (igw.getVpcId() != null) {
                                vpcRepository.findById(igw.getVpcId()).ifPresent(igw::setVpcEntity);
                                updateEntityList.add(igw);
                            }
                        })
                );

        igwRepository.saveAll(updateEntityList);
    }

    private void updateInstanceEntityAssociations() {
        Set<InstanceEntity> updateEntitySet = new HashSet<>();
        Optional.ofNullable(describeTypeListMap.get(DescribeType.INSTANCE))
                .ifPresent(valueList -> valueList.stream()
                        .filter(InstanceEntity.class::isInstance)
                        .map(InstanceEntity.class::cast)
                        .forEach(instance -> {
                            // Subnet
                            if (null != instance.getSubnetId()) {
                                subnetRepository.findById(instance.getSubnetId()).ifPresent(instance::setSubnetEntity);
                                updateEntitySet.add(instance);
                            }

                            // SecurityGroup
                            if (null != instance.getSecurityGroupId()) {
                                securityGroupRepository.findById(instance.getSecurityGroupId()).ifPresent(instance::setSecurityGroupEntity);
                                updateEntitySet.add(instance);
                            }
                        })
                );

        instanceRepository.saveAll(updateEntitySet);
    }

    private void updateEBSEntityAssociations() {
        List<EBSEntity> updateEntityList = new ArrayList<>();
        Optional.ofNullable(describeTypeListMap.get(DescribeType.EBS))
                .ifPresent(valueList -> valueList.stream()
                        .filter(EBSEntity.class::isInstance)
                        .map(EBSEntity.class::cast)
                        .forEach(ebs -> {
                            if (null != ebs.getInstanceId()) {
                                instanceRepository.findById(ebs.getInstanceId()).ifPresent(ebs::setInstanceEntity);
                                updateEntityList.add(ebs);
                            }
                        })
                );

        ebsRepository.saveAll(updateEntityList);
    }

    private void updateENIEntityAssociations() {
        List<ENIEntity> updateEntityList = new ArrayList<>();
        Optional.ofNullable(describeTypeListMap.get(DescribeType.ENI))
                .ifPresent(valueList -> valueList.stream()
                        .filter(ENIEntity.class::isInstance)
                        .map(ENIEntity.class::cast)
                        .forEach(eni -> {
                            if (null != eni.getInstanceId()) {
                                instanceRepository.findById(eni.getInstanceId()).ifPresent(eni::setInstanceEntity);
                                updateEntityList.add(eni);
                            }
                        })
                );

        eniRepository.saveAll(updateEntityList);
    }
}