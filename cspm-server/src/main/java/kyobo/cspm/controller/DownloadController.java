package kyobo.cspm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kyobo.cspm.common.dto.BaseXlsx;
import kyobo.cspm.compliance.dto.ComplianceRequestDto;
import kyobo.cspm.describe.dto.DescribeFilterDto;
import kyobo.cspm.describe.dto.ScanConfigFilterDto;
import kyobo.cspm.enums.FillDownloadCase;
import kyobo.cspm.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/downloads/xlsx")
public class DownloadController {

    private final DownloadService downloadService;


    @Operation(summary = "리소스 스캔 엑셀 다운로드", description = "필터에 따라 리소스 데이터를 엑셀 파일로 다운로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공하면 엑셀 파일 다운로드", content = @Content(mediaType = "application/vnd.ms-excel")),
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
    @PostMapping("/resource")
    public void downloadDescribeData(@RequestBody DescribeFilterDto describeFilterDto) {
       List<? extends BaseXlsx> execlList = downloadService.downloadResource(describeFilterDto);
        downloadService.setWriteDataList(execlList);
        downloadService.downloadCsv(FillDownloadCase.RESOURCE_DESCRIBE);
    }

    @Operation(summary = "취약점 스캔 결과 엑셀 다운로드", description = "필터에 따라 취약점 스캔 결과  데이터를 엑셀 파일로 다운로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공하면 엑셀 파일 다운로드", content = @Content(mediaType = "application/vnd.ms-excel")),
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
    @PostMapping("/compliance")
    public void downloadCompliance(@RequestBody ComplianceRequestDto complianceRequestDto) {
        List<? extends BaseXlsx> execlList = downloadService.downloadResource(complianceRequestDto);
        downloadService.setWriteDataList(execlList);
        downloadService.downloadCsv(FillDownloadCase.DESCRIIBE_TO_COMPLIANCE);
    }

    @Operation(summary = "사용자 엑셀 다운로드", description = "필터에 따라 사용ㅌㅌㅌ 데이터를 엑셀 파일로 다운로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공하면 엑셀 파일 다운로드", content = @Content(mediaType = "application/vnd.ms-excel")),
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
    @PostMapping("/account")
    public void downloadAccountData(@RequestBody ScanConfigFilterDto scanConfigFilterDto) {
        List<? extends BaseXlsx> execlList = downloadService.downloadResource(scanConfigFilterDto);
        downloadService.setWriteDataList(execlList);
        downloadService.downloadCsv(FillDownloadCase.ACCOUNT);
    }

}