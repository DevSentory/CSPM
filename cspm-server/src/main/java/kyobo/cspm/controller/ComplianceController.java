package kyobo.cspm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import kyobo.cspm.common.BaseResponse;
import kyobo.cspm.common.BaseResponseStatus;
import kyobo.cspm.compliance.dto.*;
import kyobo.cspm.describe.dto.AccountDescribeDto;
import kyobo.cspm.service.ComplianceAllService;
import kyobo.cspm.service.ComplianceDetailService;
import kyobo.cspm.compliance.dto.ComplianceDetailDto;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/compliances")
@Tag(name = "CSPM Compliance Controller", description = "CSPM Compliance API 컨트롤러 입니다.")
public class ComplianceController {

    private final ComplianceAllService complianceAllService;
    private final ComplianceDetailService complianceDetailService;

    /**
     * 작업 완료: 05/20 - 이루다
     * task : 취약점 탐지 후 'compliance' 테이블 전체 조회 값 전달
     *
     * @return status 200 Code & BaseResponse<List<ComplianceDescribeDto>> -> Compliance Table의 자원 전체 조회
     * Exception -> false code: 12 & message
     */
    @Operation(summary = "Listing All Compliance Data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공 또는 빈 객체 반환", content = @Content(schema = @Schema(
                    oneOf = {
                            ComplianceRequestDto.class,
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
                                            "    \"accountId\": [\n" +
                                            "      \"339712736815\"\n" +
                                            "    ],\n" +
                                            "    \"data\": [\n" +
                                            "      {\n" +
                                            "        \"scanTime\": \"2024-06-04 11:27\",\n" +
                                            "        \"rid\": \"TEST\",\n" +
                                            "        \"vulnerabilityStatus\": \"Exception\",\n" +
                                            "        \"accountName\": \"test\",\n" +
                                            "        \"accountId\": \"339712736815\",\n" +
                                            "        \"resourceId\": \"sg-0139482984dae9310\",\n" +
                                            "        \"client\": \"test\",\n" +
                                            "        \"policySeverity\": \"High\",\n" +
                                            "        \"policyTitle\": \"보안그룹 Inbound Any open(0.0.0.0) 점검\",\n" +
                                            "        \"policyCompliance\": \"PCI DSS 1.2.1, Nist.800-53.R5 AC-4, CIS AWS Foundations Benchmark 4.1\"\n" +
                                            "      }\n" +
                                            "    ],\n" +
                                            "    \"accountName\": [\n" +
                                            "      \"test\"\n" +
                                            "    ],\n" +
                                            "    \"client\": [\n" +
                                            "      \"test\"\n" +
                                            "    ]\n" +
                                            "  }\n" +
                                            "}"
                            ),
                            @ExampleObject(name = "EmptyResponseExample", value = "{\"isSuccess\": false, \"code\": 12, \"message\": \"Result Empty, 값이 비어있습니다.\"}")

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
    public BaseResponse<ConcurrentHashMap<String, List<?>>> getComplianceList(@RequestBody ComplianceRequestDto complianceRequestDto) {
        return complianceAllService.complianceFilterList(complianceRequestDto);
    }

    /**
     * 작업 완료: 05/20 - 이승주
     * task : 'compliance' 테이블 상세 조회 값 전달
     *
     * @return status 200 Code BaseResponse<ComplianceDetailDto> -> Compliance Table의 자원 상세 조회
     * Exception -> false code: 12 & message
     */
    @Operation(summary = "Get Compliance Detail Data",
            parameters = {
                    @Parameter(name = "resourceId", description = "resource Id 직접 입력할 것", required = true, example = "sg-0139482984dae9310"),
                    @Parameter(name = "scanTime", description = "scan Time 직접 입력할 것", required = true, example = ""),
                    @Parameter(name = "accountId", description = "account Id", required = true, example = "339712736815"),
                    @Parameter(name = "accountName", description = "account Name", required = true, example = "test")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공 또는 빈 객체 반환", content = @Content(schema = @Schema(implementation = BaseResponseStatus.class),
                    examples = {@ExampleObject(
                            name = "SuccessExample",
                            value = "{\n" +
                                    "  \"isSuccess\": true,\n" +
                                    "  \"code\": 0,\n" +
                                    "  \"message\": \"Success\",\n" +
                                    "  \"result\": \"성공\"\n" +
                                    "}"
                    ), @ExampleObject(
                            name = "BadResponseExample",
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
    @GetMapping("/{resourceId}/{scanTime}/{accountId}/{accountName}")
    public BaseResponse<ComplianceDetailDto> getDetailCompliance(@PathVariable String resourceId, @PathVariable String scanTime, @PathVariable String accountId, @PathVariable String accountName) {
        return complianceDetailService.findDetailCompliance(resourceId, scanTime, accountId, accountName);
    }


    /**
     * 작업 완료: 05/20 - 이승주
     * task : complianceExceptionReqDto 받아 'compliance_exception' 테이블 생성 API
     *
     * @return success code: 0 & message
     * Exception -> false code: 12 & message
     * 수정 작업 완료: 05/24 - 이루다
     */
    @Operation(summary = "Create Compliance Exception")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공 또는 빈 객체 반환", content = @Content(schema = @Schema(
                    oneOf = {
                            ComplianceExceptionReqDto.class,
                            BaseResponseStatus.class,
                    }),
                    examples = {@ExampleObject(
                            name = "SuccessExample",
                            value = "{\n" +
                                    "  \"isSuccess\": true,\n" +
                                    "  \"code\": 0,\n" +
                                    "  \"message\": \"Success\",\n" +
                                    "  \"result\": {\n" +
                                    "    \"rid\": \"ASACD\",\n" +
                                    "    \"scanTime\": \"2024-06-03 09:20\",\n" +
                                    "    \"policyCompliance\": \"PCI DSS 6.5.10, ISO/IEC 27001:2013 A.9.4, NIST SP 800-53 SC-13, CIS AWS Foundations Benchmark 4.1\",\n" +
                                    "    \"policyType\": \"접근 관리\",\n" +
                                    "    \"policySeverity\": \"High\",\n" +
                                    "    \"policyTitle\": \"인스턴스 Key-pair 미사용\",\n" +
                                    "    \"accountName\": \"ffffff\",\n" +
                                    "    \"accountId\": \"339712736815\",\n" +
                                    "    \"service\": \"EC2\",\n" +
                                    "    \"resourceId\": \"i-0bb932d170488169a\",\n" +
                                    "    \"policyDescription\": \"인스턴스에 Key-pair를 사용하지 않는 경우, SSH를 통한 보안 연결이 불가능하며, 인스턴스에 대한 접근이 불가능해질 수 있습니다. 이는 보안 및 관리상의 문제가 발생할 수 있습니다.\",\n" +
                                    "    \"policyResponse\": \"1. 인스턴스 생성 시 반드시 Key-pair를 생성하여 사용합니다. 이를 통해 SSH를 통한 안전한 접근이 가능해집니다. 2. 기존 인스턴스에 Key-pair를 추가하려면, 새 인스턴스를 생성하고 데이터를 마이그레이션하거나, 기존 인스턴스에 직접 접근할 수 있는 다른 보안 수단을 사용합니다. 3. Key-pair를 안전하게 관리하고 주기적으로 변경하여 보안 수준을 유지합니다. 4. Key-pair 관리 정책을 수립하고 준수하여 인스턴스 접근에 대한 통제력을 높입니다.\"\n" +
                                    "  }\n" +
                                    "}"
                    ),
                            @ExampleObject(
                                    name = "BadResponseExample",
                                    value = "{\n" +
                                            "  \"isSuccess\": false,\n" +
                                            "  \"code\": 12,\n" +
                                            "  \"message\": \"Result Empty, 값이 비어있습니다.\"\n" +
                                            "}"
                            )
                    })),
            @ApiResponse(responseCode = "400", description = "ClientBadRequest", content = @Content(
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
    @PostMapping("/exception")
    public BaseResponse<String> createException(@RequestBody ComplianceExceptionReqDto complianceExceptionReqDto) {
        return complianceDetailService.createException(complianceExceptionReqDto);
    }


    /**
     * 작업 완료: 05/21 - 이승주
     * task : 'compliance_exception' 테이블 상세조회 값 전달
     *
     * @return status 200 Code & BaseResponse<ComplianceExceptionResDto> ->compliance_exception의 자원 상세 조회
     * Exception -> false code: 12 & message
     */
    @Operation(summary = "Get Exception Detail Data",
            parameters = {
                    @Parameter(name = "resourceId", description = "resource Id 직접 입력할 것", required = true, example = "sg-0139482984dae9310"),
                    @Parameter(name = "policyTitle", description = "policyTitle 직접 입력할 것", required = true, example = "보안그룹 Inbound Any open(0.0.0.0) 점검"),
                    @Parameter(name = "accountId", description = "account Id", required = true, example = "339712736815"),
                    @Parameter(name = "accountName", description = "account Name", required = true, example = "test")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공 또는 빈 객체 반환", content = @Content(schema = @Schema(
                    oneOf = {
                            ComplianceExceptionDto.class,
                            BaseResponseStatus.class,
                    }),
                    examples = {@ExampleObject(
                            name = "SuccessExample",
                            value = "{\n" +
                                    "  \"isSuccess\": true,\n" +
                                    "  \"code\": 0,\n" +
                                    "  \"message\": \"Success\",\n" +
                                    "  \"result\": {\n" +
                                    "    \"id\": 1,\n" +
                                    "    \"exceptionTime\": \"2024-05-31 17:03\",\n" +
                                    "    \"exceptionContent\": \"test\",\n" +
                                    "    \"exceptionHandler\": \"admin\",\n" +
                                    "    \"policyTitle\": \"보안그룹 Inbound Any open(0.0.0.0) 점검\",\n" +
                                    "    \"resourceId\": \"sg-0139482984dae9310\",\n" +
                                    "    \"accountId\": \"339712736815\",\n" +
                                    "    \"accountName\": \"test\"\n" +
                                    "  }\n" +
                                    "}\n"
                    ),
                            @ExampleObject(
                                    name = "BadResponseExample",
                                    value = "{\n" +
                                            "  \"isSuccess\": false,\n" +
                                            "  \"code\": 12,\n" +
                                            "  \"message\": \"Result Empty, 값이 비어있습니다.\"\n" +
                                            "}"
                            )
                    })),
            @ApiResponse(responseCode = "400", description = "ClientBadRequest", content = @Content(
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
    @GetMapping("/exception/{resourceId}/{policyTitle}/{accountId}/{accountName}")
    public BaseResponse<ComplianceExceptionDto> getDetailException(@PathVariable String resourceId, @PathVariable String policyTitle, @PathVariable String accountId, @PathVariable String accountName) {
        return complianceDetailService.getException(resourceId, policyTitle, accountId, accountName);
    }
}

