package kyobo.cspm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kyobo.cspm.common.BaseResponse;
import kyobo.cspm.common.BaseResponseStatus;
import kyobo.cspm.describe.dto.DescribeAccount;
import kyobo.cspm.describe.dto.DescribeFilterDto;
import kyobo.cspm.service.DetailService;
import kyobo.cspm.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/resources")
@Tag(name = "CSPM Resource controller", description = "CSPM AWS Resource API 컨트롤러 입니다.")
public class ResourceController {

    private final ResourceService serviceDescribe;
    private final DetailService detailService;


    /**
     * [2024-05-? Donggyu 작성]
     * [2024-05-22 승근 수정]
     * task : AWS 자원 스캔 및 저장 후 'resource_describe' 테이블 데이터 전체 조회 값 전달, 취약점 조회후 compliance 테이블에 저장
     * - 수정 내역:  compliance 테이블에 저장이 완료되도록 서비스를 추가함
     */
    @Operation(summary = "Start describe AWS resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponseStatus.class),
                            examples = {@ExampleObject(
                                    name = "AWS Resource Scan 성공",
                                    value = "{\"isSuccess\": true, \"code\": \"1\", \"message\": \"Success with return\"}"),
                                    @ExampleObject(
                                            name = "BadRequestExample",
                                            value = "{\n" +
                                                    "  \"isSuccess\": \"false\",\n" +
                                                    "  \"code\": \"12\",\n" +
                                                    "  \"message\": \"Result Empty, 값이 비어있습니다.\"\n" +
                                                    "}"
                                    )})),
            @ApiResponse(responseCode = "400", description = "ClientBadRequest", content = @Content(schema = @Schema(implementation = BaseResponseStatus.class),
                    examples = @ExampleObject(
                            name = "ClientBadRequestExample",
                            value = "{\n" +
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
    @PostMapping
    public BaseResponse<Void> saveDescribe(@RequestBody List<DescribeAccount> describeAccountList) {
        return serviceDescribe.startDescribe(describeAccountList);
    }

    /**
     * [2024.05.09 작업 완료 - Donggyu]
     * task : DescribeEntity 테이블 전체 조회
     * [2024.5/17 수정 완료 - 승주]
     * - 수정 내역 : 반환 타입을 HaspMap형식으로 변경
     */
    @Operation(summary = "Resource 리스트 반환 및 필터에 따른 값 반환", description = "client, accountName, accountId, service, fromDate, toDate, searchDate, resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공 또는 빈 객체 반환", content = @Content(schema = @Schema(
                    oneOf = {
                            DescribeFilterDto.class,
                            BaseResponseStatus.class
                    }),
                    examples = {@ExampleObject(
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
                                    "        \"resultType\": \"NONE\",\n" +
                                    "        \"message\": \"\",\n" +
                                    "        \"scanTime\": \"2024-05-31 17:02\",\n" +
                                    "        \"createTime\": \"-\",\n" +
                                    "        \"client\": \"ASAC\",\n" +
                                    "        \"accountId\": \"339712736815\",\n" +
                                    "        \"accountName\": \"test\",\n" +
                                    "        \"serviceGroup\": \"S3\",\n" +
                                    "        \"resourceId\": \"test-pulbic-bucket-2405141\",\n" +
                                    "        \"tag\": \"-\"\n" +
                                    "      },\n" +
                                    "    ],\n" +
                                    "    \"accountName\": [\n" +
                                    "      \"정승근\"\n" +
                                    "    ],\n" +
                                    "    \"client\": [\n" +
                                    "      \"ASAC\"\n" +
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
                            )
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
    public BaseResponse<ConcurrentHashMap<String, List<?>>> getDescribeList(@RequestBody DescribeFilterDto describeFilterDto) {
        return serviceDescribe.getAwsDescribeResultList(describeFilterDto);
    }


    /**
     * 스캔 결과 페이지에서 상세 조회 버튼을 눌렀을 때 호출되는 API
     *
     * @return status 200 Code & List<Object> -> Object는 각 자원의 DTO
     * Exception -> false code: 12 & message
     */
    @Operation(summary = "Get Resource Detail data",
            parameters = {
                    @Parameter(name = "resourceId", description = "resource Id", required = true, example = "igw-075e0be7d50fb2782"),
                    @Parameter(name = "scanTime", description = "scan Time db 저장된 것에 알맞게 적을 것", required = true, example = ""),
                    @Parameter(name = "accountId", description = "account id", required = true, example = "339712736815"),
                    @Parameter(name = "accountName", description = "account Name", required = true, example = "test")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class),
                            examples = {@ExampleObject(
                                    name = "Resource Id에 따른 자원 전체 Data",
                                    value = "{\"isSuccess\": \"ture\", \"code\": \"0\", \"message\": \"Success\"}"),
                                    @ExampleObject(
                                            name = "BadRequestExample",
                                            value = "{\n" +
                                                    "  \"isSuccess\": \"false\",\n" +
                                                    "  \"code\": \"12\",\n" +
                                                    "  \"message\": \"Result Empty, 값이 비어있습니다.\"\n" +
                                                    "}")
                            })}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BaseResponseStatus.class),
                    examples = @ExampleObject(
                            name = "ClientBadRequestExample",
                            value = "{\n" +
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
    @GetMapping("/{resourceId}/{scanTime}/{accountId}/{accountName}")
    public BaseResponse<String> getDetailDescribe(@PathVariable String resourceId, @PathVariable String scanTime, @PathVariable String accountId, @PathVariable String accountName) {
        return detailService.findDetailResource(resourceId, scanTime, accountId, accountName);
    }
}