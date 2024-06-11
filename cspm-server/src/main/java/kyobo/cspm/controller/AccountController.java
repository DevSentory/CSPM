package kyobo.cspm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kyobo.cspm.common.BaseResponse;
import kyobo.cspm.common.BaseResponseStatus;
import kyobo.cspm.describe.dto.*;
import kyobo.cspm.describe.entity.AccountsEntity;
import kyobo.cspm.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts")
@Tag(name = "CSPM account controller",
        description = "CSPM Account API 컨트롤러 입니다. path : localhost:8080/accounts로 시작합니다. " +
                "1. accessKey+ secretKey를 가지고 accountId 조회" +
                "2. 고객사 등록" + "3. 고객사 전체 리스틑 반환" + "4. 고객사 업데이트 내용 저장" + "5. 고객사 업데이트 내용 응답" + "6. 고객사 삭제")
public class AccountController {

    private final AccountService accountService;

    /**
     * [2024.05.?? 작업 완료 - 동규]
     * task : accountId, accountName(=alias) 값 반환
     */

    @Operation(summary = "accountId 조회", description = "accessKey와 secretKey로 accountId를 조회하는 메서드입니다.",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "region", description = "AWS 지역", required = true, example = "ap-northeast-2"),
                    @Parameter(in = ParameterIn.HEADER, name = "access-key", description = "AWS Access Key", required = true, example = "-"),
                    @Parameter(in = ParameterIn.HEADER, name = "secret-access-key", description = "AWS Secret Key", required = true, example = "-")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = BaseResponseStatus.class),
                    examples = {@ExampleObject(
                            name = "SuccessRequestExample",
                            value = "{\"success\": true, \"id\": 339712736815\"}"
                    ),
                            @ExampleObject(
                                    name = "BadRequestExample",
                                    value = "{\n" +
                                            "  \"success\":false,\n" +
                                            "  \"id\":\"\",\n" +
                                            "  \"message\":\"The security token included in the request is invalid. (Service: Sts, Status Code: 403, Request ID: f1669756-ae88-4804-8ed3-5fba8fcd568e)\"\n" +
                                            "}"
                            )
                    })),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(
                    examples = @ExampleObject(
                            name = "ClientBadRequestExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:36:10.860+00:00\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"path\": \"/accounts\"/info\n" +
                                    "}"
                    ))),
            @ApiResponse(responseCode = "500", description = "서버 응답 실패", content = @Content(
                    examples = @ExampleObject(
                            name = "ServerBadResponseExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:51:14.175+00:00\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Server Error\",\n" +
                                    "  \"path\": \"/accounts/info\"\n" +
                                    "}"
                    ))),
    })
    @GetMapping("/info")
    public String getAccountIdAndName(@RequestHeader Map<String, String> awsInfo) {
        return accountService.getAwsAccountId(awsInfo);
    }

    /**
     * [2024.05.10 작업 완료 - 동규]
     * [2024.05.24 수정 완료 - 승근]
     * - 수정 내역 : accessKey, secretKey 암호화를 service에서 처리하도록 변경
     * task : 고객사 등록
     */
    @Operation(summary = "고객사 등록", description = "고객사, CODE, accountName, accessKey, secretKey, region, comment, accountId를 입력받아서 DataBase에 저장하는 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(
                    oneOf = {
                            AccountDto.class,
                            BaseResponseStatus.class,
                    }),
                    examples = {@ExampleObject(
                            name = "SuccessRequestExample",
                            value = "{\n" +
                                    "  \"isSuccess\": true,\n" +
                                    "  \"code\": 0,\n" +
                                    "  \"message\": \"Success\",\n" +
                                    "  \"result\": {\n" +
                                    "    \"id\": 1,\n" +
                                    "    \"accountId\": \"339712736815\",\n" +
                                    "    \"client\": \"test\",\n" +
                                    "    \"code\": \"TEST\",\n" +
                                    "    \"accountName\": \"-\",\n" +
                                    "    \"accessKey\": \"PzImb8WU4+QzmVtbP4mz0QonCw38kMXhhgdPriqIX1w=\",\n" +
                                    "    \"secretKey\": \"NaPyWyT2aa5LVP1nH2kPawUYlJhOq3bhTKtJzaxb5wyNZC0VQdgcZtYZgkHoTl0r\",\n" +
                                    "    \"region\": \"ap-northeast-2\",\n" +
                                    "    \"comment\": \"test\",\n" +
                                    "    \"registerTime\": \"2024-06-04 15:25\",\n" +
                                    "    \"lastUpdateDescribeTime\": \"-\",\n" +
                                    "    \"describeResult\": \"-\"\n" +
                                    "  }\n" +
                                    "}"
                    ),
                            @ExampleObject(name = "BadRequestExample", value = "{\n" +
                                    "  \"isSuccess\": false,\n" +
                                    "  \"code\": 4,\n" +
                                    "  \"message\": \"AccountName이 중복되었습니다.\"\n" +
                                    "}")
                    })),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
                    examples = @ExampleObject(
                            name = "ClientBadRequestExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:36:10.860+00:00\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"path\": \"/accounts\"\n" +
                                    "}}"
                    ))),
            @ApiResponse(responseCode = "500", description = "서버 응답 실패", content = @Content(
                    examples = @ExampleObject(
                            name = "ServerBadResponseExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:51:14.175+00:00\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Server Error\",\n" +
                                    "  \"path\": \"/accounts/info\"\n" +
                                    "}"
                    ))),
    })
    @PostMapping
    public BaseResponse<AccountsEntity> createAccount(@RequestBody AccountDto accountDto) {
        return accountService.accountSaveToReturn(accountDto);
    }

    /**
     * [2024.05.?? 작업 완료 - 승근]
     * [2024.05.10 수정 완료 - 동규]
     * [2024.05.14 수정 완료 - 승근]
     * [2024.05.17 수정 완료  - 승주]
     * - 수정 내역 : accounts 테이블에서 필요 데이터를 반환하도록 변경
     * - 수정 내역 : 기본 accountDto에서 accountDescribeDto로 변경함
     * - 수정 내역 : 반환 타입을 HaspMap형식으로 변경
     * task : 고객사 전체 리스트를 반환
     */
    @Operation(summary = "고객사 전체 리스트 반환 및 필터에 따른 값 반환", description = "고객사, accountName, accountId, registerTime, lastUpdateDescribeTime, describeResult 전체 고객사 리스트를 반환하는 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공 또는 빈 객체 반환", content = @Content(schema = @Schema(
                    oneOf = {
                            AccountDescribeDto.class,
                            BaseResponseStatus.class,
                    }),
                    examples = {
                            @ExampleObject(
                                    name = "SuccessExample",
                                    value = "{\n" +
                                            "  \"isSuccess\": true,\n" +
                                            "  \"code\": 0,\n" +
                                            "  \"message\": \"Success\",\n" +
                                            "  \"result\": {\n" +
                                            "    \"data\": [\n" +
                                            "      {\n" +
                                            "        \"client\": \"test\",\n" +
                                            "        \"accountName\": \"-\",\n" +
                                            "        \"accountId\": \"339712736815\",\n" +
                                            "        \"registerTime\": \"2024-06-04 15:57\",\n" +
                                            "        \"lastUpdateDescribeTime\": \"-\",\n" +
                                            "        \"describeResult\": \"-\"\n" +
                                            "      }\n" +
                                            "    ],\n" +
                                            "    \"accountName\": [\n" +
                                            "      \"-\"\n" +
                                            "    ],\n" +
                                            "    \"client\": [\n" +
                                            "      \"test\"\n" +
                                            "    ]\n" +
                                            "  }\n" +
                                            "}"
                            ),
                            @ExampleObject(
                                    name = "BadRequestExample",
                                    value = "{\n" +
                                            "  \"isSuccess\": false,\n" +
                                            "  \"code\": 12,\n" +
                                            "  \"message\": \"Result Empty, 값이 비어있습니다.\"\n" +
                                            "}")

                    })),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
                    examples = @ExampleObject(
                            name = "ClientBadRequestExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:36:10.860+00:00\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"path\": \"/accounts\"\n" +
                                    "}}"
                    ))),
            @ApiResponse(responseCode = "500", description = "서버 응답 실패", content = @Content(
                    examples = @ExampleObject(
                            name = "ServerBadResponseExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:51:14.175+00:00\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Server Error\",\n" +
                                    "  \"path\": \"/accounts/info\"\n" +
                                    "}"
                    ))),
    })
    @PostMapping("/list")
    public BaseResponse<ConcurrentHashMap<String, List<?>>> getAccountList(@RequestBody ScanConfigFilterDto scanConfigFilterDto) {
        return accountService.accountAllList(scanConfigFilterDto);
    }

    /**
     * [2024.05.?? 작업 완료 - 승근]
     * [2024.05.10 수정 완료 - 동규]
     * - 수정 내역 : RequestBody 데이터 타입을 AccountDto 값으로 변경
     * task : 고객사 업데이트
     */
    @Operation(summary = "고객사 수정", description = "고객사, CODE,accountName, accountId, comment, region 수정사항을 입력 받아 업데이트하는 메서드입니다.",
            parameters = @Parameter(name = "accountId", description = "account Id", required = true, example = "339712736815"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(
                    oneOf = {AccountDto.class,
                            BaseResponseStatus.class
                    }),
                    examples = {
                            @ExampleObject(
                                    name = "SuccessExample",
                                    value = "{\n" +
                                            "  \"isSuccess\": true,\n" +
                                            "  \"code\": 1,\n" +
                                            "  \"message\": \"Success with return\"\n" +
                                            "}"),
                            @ExampleObject(name = "BadRequest", value = "{\n" +
                                    "  \"isSuccess\": false,\n" +
                                    "  \"code\": 12,\n" +
                                    "  \"message\": \"Result Empty, 값이 비어있습니다.\"\n" +
                                    "}")})),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
                    examples = @ExampleObject(
                            name = "ClientBadRequestExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:36:10.860+00:00\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"path\": \"/accounts\"\n" +
                                    "}}"
                    ))),
            @ApiResponse(responseCode = "500", description = "서버 응답 실패", content = @Content(
                    examples = @ExampleObject(
                            name = "ServerBadResponseExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:51:14.175+00:00\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Server Error\",\n" +
                                    "  \"path\": \"/accounts/info\"\n" +
                                    "}"
                    ))),
    })
    @PutMapping("/{accountId}/{accountName}")
    public BaseResponse<Void> updateAccount(@PathVariable String accountId, @PathVariable String accountName, @RequestBody AccountDto accountDto) {
        return accountService.accountUpdate(accountId, accountName, accountDto);
    }

    /**
     * [2024.05.14 작업 완료 - 승근]
     * task : 업데이트할 고객사 데이터 반환
     */
    @Operation(summary = "고객사 수정 정보 반환", description = "고객사, CODE,accountName, accountId, comment, region 선택한 고객사의 정보를 수정 페이지에 반환하는 메서드입니다.",
            parameters = @Parameter(name = "accountId", description = "account Id", required = true, example = "339712736815"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = AccountDto.class),
                    examples = @ExampleObject(
                            name = "SuccessExample",
                            value = "{\n" +
                                    "  \"isSuccess\": true,\n" +
                                    "  \"code\": 0,\n" +
                                    "  \"message\": \"Success\",\n" +
                                    "  \"result\": {\n" +
                                    "    \"client\": \"test\",\n" +
                                    "    \"code\": \"TEST\",\n" +
                                    "    \"accountName\": \"-\",\n" +
                                    "    \"accessKey\": \"-\",\n" +
                                    "    \"secretKey\": \"-\",\n" +
                                    "    \"region\": \"ap-northeast-2\",\n" +
                                    "    \"comment\": \"test\",\n" +
                                    "    \"accountId\": \"339712736815\"\n" +
                                    "  }\n" +
                                    "}"
                    ))),
            @ApiResponse(responseCode = "400", description = "ClientBadRequest", content = @Content(
                    examples = @ExampleObject(
                            name = "ClientBadRequestExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:36:10.860+00:00\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"path\": \"/accounts\"\n" +
                                    "}"
                    ))),
            @ApiResponse(responseCode = "500", description = "서버 응답 실패", content = @Content(
                    examples = @ExampleObject(
                            name = "ServerBadResponseExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:51:14.175+00:00\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Server Error\",\n" +
                                    "  \"path\": \"/accounts/info\"\n" +
                                    "}"
                    ))),
    })
    @GetMapping("/{accountId}/{accountName}")
    public BaseResponse<AccountDto> getAccount(@PathVariable String accountId, @PathVariable String accountName) {
        return BaseResponse.success(accountService.accountGet(accountId, accountName));
    }


    /**
     * [2024.05.?? 작업 완료 - 승근]
     * task : 고객사 업데이트
     */
    @Operation(summary = "고객사 삭제", description = "accountId 리스트를 받아서 고개사를 삭제하는 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = BaseResponseStatus.class),
                    examples = {@ExampleObject(
                            name = "SuccessRequestExample",
                            value = "{\"isSuccess\": true, \"code\": 01, \"message\": \"Success with return\"}"

                    ), @ExampleObject(
                            name = "BadRequestExample",
                            value = "{\n" +
                                    "  \"isSuccess\": false,\n" +
                                    "  \"code\": 12,\n" +
                                    "  \"message\": \"Result Empty, 값이 비어있습니다.\"\n" +
                                    "}")
                    })),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseResponseStatus.class),
                    examples = @ExampleObject(
                            name = "ClientBadRequestExample",
                            value = "\t\n" +
                                    "Error: response status is 400\n" +
                                    "\n" +
                                    "Response body\n" +
                                    "Download\n" +
                                    "{\n" +
                                    "  \"timestamp\": \"2024-06-04T07:27:32.520+00:00\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"path\": \"/accounts\"\n" +
                                    "}"
                    ))),
            @ApiResponse(responseCode = "500", description = "서버 응답 실패", content = @Content(
                    examples = @ExampleObject(
                            name = "ServerBadResponseExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:51:14.175+00:00\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Server Error\",\n" +
                                    "  \"path\": \"/accounts/info\"\n" +
                                    "}"
                    ))),

    })
    @DeleteMapping
    public BaseResponse<Void> deleteAccount(@RequestBody List<DescribeAccount> describeAccountList) {
        return accountService.accountDelete(describeAccountList);
    }


    @Operation(summary = "스캔 오류 반환", description = "exceptionCodes, exceptionMessages 선택한 고객사의 오류를 반환하는 메서드입니다.",
            parameters = {
                    @Parameter(name = "accountId", description = "account Id", required = true, example = "339712736815"),
                    @Parameter(name = "scanTime", description = "scan Time 직접 입력할 것", required = true, example = ""),
                    @Parameter(name = "accountName", description = "account Name", required = true, example = "-")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(
                    oneOf = {ErrorLogDto.class,
                            BaseResponseStatus.class}),
                    examples = {
                            @ExampleObject(
                                    name = "SuccessExample",
                                    value = "{\n" +
                                            "  \"isSuccess\": true,\n" +
                                            "  \"code\": 0,\n" +
                                            "  \"message\": \"Success\",\n" +
                                            "  \"result\": {\n" +
                                            "    \"exceptionCodes\": [\n" +
                                            "      \"FAIL_UNAUTHORIZED_OPERATION\",\n" +
                                            "      \"FAIL_ACCESS_DENIED\"\n" +
                                            "    ],\n" +
                                            "    \"exceptionMessages\": [\n" +
                                            "      \"when calling the DescribeVolumes operation: You are not authorized to perform this operation. User: arn:aws:iam::339712736815:user/cspm_access is not authorized to perform: ec2:DescribeVolumes because no identity-based policy allows the ec2:DescribeVolumes action\",\n" +
                                            "      \"when calling the DescribeLoadBalancers operation: User: arn:aws:iam::339712736815:user/cspm_access is not authorized to perform: elasticloadbalancing:DescribeLoadBalancers because no identity-based policy allows the elasticloadbalancing:DescribeLoadBalancers action\"\n" +
                                            "    ],\n" +
                                            "    \"exceptionService\": [\n" +
                                            "      \"INSTANCE\",\n" +
                                            "      \"EBS\",\n" +
                                            "      \"IGW\",\n" +
                                            "      \"ROUTE\",\n" +
                                            "      \"ENI\",\n" +
                                            "      \"VPC\",\n" +
                                            "      \"SECURITY_GROUP\",\n" +
                                            "      \"SUBNET\",\n" +
                                            "      \"S3\"\n" +
                                            "    ]\n" +
                                            "  }\n" +
                                            "}"
                            ),
                            @ExampleObject(
                                    name = "BadRequestExample",
                                    value = "{\n" +
                                            "  \"isSuccess\": false,\n" +
                                            "  \"code\": 12,\n" +
                                            "  \"message\": \"Result Empty, 값이 비어있습니다.\"\n" +
                                            "}"
                            )}

            )),
            @ApiResponse(responseCode = "400", description = "ClientBadRequest", content = @Content(
                    examples = @ExampleObject(
                            name = "ClientBadRequestExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:36:10.860+00:00\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"path\": \"/accounts\"\n" +
                                    "}"
                    ))),
            @ApiResponse(responseCode = "500", description = "서버 응답 실패", content = @Content(
                    examples = @ExampleObject(
                            name = "ServerBadResponseExample",
                            value = "{\n" +
                                    "  \"timestamp\": \"2024-06-04T06:51:14.175+00:00\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Server Error\",\n" +
                                    "  \"path\": \"/accounts/info\"\n" +
                                    "}"
                    ))),

    })
    @GetMapping("/error/{accountId}/{scanTime}/{accountName}")
    public BaseResponse<ErrorLogDto> getAccountError(@PathVariable String accountId, @PathVariable String scanTime, @PathVariable String accountName) {
        ErrorLogDto resultErrorLogs = accountService.accountGetErrorLogs(accountId, scanTime, accountName);
        if (resultErrorLogs.getExceptionCodes().isEmpty()) {
            return BaseResponse.nonVoidError(BaseResponseStatus.EMPTY_RESOURCE);
        }
        return BaseResponse.success(resultErrorLogs);
    }

}