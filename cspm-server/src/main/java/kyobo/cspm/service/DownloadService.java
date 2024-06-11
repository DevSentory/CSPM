package kyobo.cspm.service;

import jakarta.servlet.http.HttpServletResponse;
import kyobo.cspm.common.BaseResponse;
import kyobo.cspm.common.BaseResponseStatus;
import kyobo.cspm.common.dto.AccountXlsxDto;
import kyobo.cspm.common.dto.BaseXlsx;
import kyobo.cspm.common.dto.ComplianceXlsxDto;
import kyobo.cspm.common.dto.DescribeXlsxDto;
import kyobo.cspm.compliance.dto.ComplianceRequestDto;
import kyobo.cspm.describe.dto.DescribeFilterDto;
import kyobo.cspm.describe.dto.ScanConfigFilterDto;
import kyobo.cspm.describe.repository.AccountsRepository;
import kyobo.cspm.describe.repository.DescribeRepository;
import kyobo.cspm.compliance.repository.ComplianceRepository;
import kyobo.cspm.enums.FillDownloadCase;
import kyobo.cspm.service.components.PageFilterData;
import kyobo.cspm.utils.EnvironmentUtils;
import kyobo.cspm.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloadService {

    private final AccountsRepository accountsRepository;
    private Sheet sheet;
    private final EnvironmentUtils environmentUtils;
    private final HttpServletResponse httpServletResponse;
    private final DescribeRepository describeRepository;
    private final ComplianceRepository complianceRepository;

    @Setter
    private List<? extends BaseXlsx> writeDataList;


    public void downloadCsv(FillDownloadCase aCase) {
        String localTime = StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm");

        // 파일명
        String fileName = aCase.getFileName() +
                (environmentUtils.isDevProfileActive() ? "_dev_" : "_") +
                localTime + ".xlsx";

        // attachment : 파일 다운로드 타입으로 설정 (실제 파일 다운로드 위치는 클라이언트 측에서 결정한다.)
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (Workbook workbook = new XSSFWorkbook()) {
            sheet = workbook.createSheet("Data");
            switch (aCase) {
                case ACCOUNT -> accountDataDownload();
                case RESOURCE_DESCRIBE -> describeDataDownload();
                case DESCRIIBE_TO_COMPLIANCE -> complianceDataDownload();
                default -> throw new IllegalArgumentException("Invalid case: " + aCase);
            }

            // 파일 쓰기
            workbook.write(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            BaseResponse.error(BaseResponseStatus.FAILURE);
        }
    }

    private void createHeaderRow(String[] headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 모든 열에 대해 자동 너비 조정
        for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void accountDataDownload() {
        String[] headers = {"고객사", "Account Name", "Account ID", "Account 등록 시간", "최근 리소스 스캔 시간", "스캔 결과"};
        createHeaderRow(headers);

        int rowNum = 1;
        for (Object data : writeDataList) {
            AccountXlsxDto dto = (AccountXlsxDto) data;
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(dto.getClient());
            dataRow.createCell(1).setCellValue(dto.getAccountName());
            dataRow.createCell(2).setCellValue(dto.getAccountId());
            dataRow.createCell(3).setCellValue(dto.getRegisterTime());
            dataRow.createCell(4).setCellValue(dto.getLastUpdateDescribeTime());
            dataRow.createCell(5).setCellValue(dto.getDescribeResultType());
        }
    }

    private void describeDataDownload() {
        String[] headers = {"스캔 날짜", "리소스 생성 날짜", "고객사", "Account Name", "Account ID", "Service", "Resource ID", "tag", "Json"};
        createHeaderRow(headers);

        int rowNum = 1;
        for (Object data : writeDataList) {
            DescribeXlsxDto dto = (DescribeXlsxDto) data;
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(dto.getScanTime());
            dataRow.createCell(1).setCellValue(dto.getResourceRegisterTime());
            dataRow.createCell(2).setCellValue(dto.getClient());
            dataRow.createCell(3).setCellValue(dto.getAccountName());
            dataRow.createCell(4).setCellValue(dto.getAccountId());
            dataRow.createCell(5).setCellValue(dto.getService());
            dataRow.createCell(6).setCellValue(dto.getResourceId());
            dataRow.createCell(7).setCellValue(dto.getTag());
            dataRow.createCell(8).setCellValue(dto.getResourceJson());

        }
    }

    private void complianceDataDownload() {
        String[] headers = {"스캔 날짜", "RID", "취약점 상태", "Account Name", "Account ID", "Resource ID", "고객사", "취약 등급", "취약점 이름", "Compliance", "상세보기"};
        createHeaderRow(headers);

        int rowNum = 1;
        for (Object data : writeDataList) {
            ComplianceXlsxDto dto = (ComplianceXlsxDto) data;
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(dto.getScanTime());
            dataRow.createCell(1).setCellValue(dto.getRid());
            dataRow.createCell(2).setCellValue(dto.getVulnerabilityStatus());
            dataRow.createCell(3).setCellValue(dto.getAccountName());
            dataRow.createCell(4).setCellValue(dto.getAccountId());
            dataRow.createCell(5).setCellValue(dto.getResourceId());
            dataRow.createCell(6).setCellValue(dto.getClient());
            dataRow.createCell(7).setCellValue(dto.getPolicyType());
            dataRow.createCell(8).setCellValue(dto.getPolicySeverity());
            dataRow.createCell(9).setCellValue(dto.getPolicyCompliance());
            dataRow.createCell(10).setCellValue(dto.getPolicyTitle());
            dataRow.createCell(11).setCellValue(dto.getPolicyDescription());
            dataRow.createCell(12).setCellValue(dto.getPolicyResponse());

        }
    }

    public List<DescribeXlsxDto> downloadResource(DescribeFilterDto describeFilterDto){
        return describeRepository.findAllQueryDescription(describeFilterDto).stream().map(DescribeXlsxDto::of).toList();
    }

    public List<? extends BaseXlsx> downloadResource(ComplianceRequestDto complianceRequestDto) {
        return complianceRepository.findQueryCompliance(complianceRequestDto).stream().map(ComplianceXlsxDto::of).toList();
    }

    public List<? extends BaseXlsx> downloadResource(ScanConfigFilterDto scanConfigFilterDto) {
        return accountsRepository.findAllQueryDescription(scanConfigFilterDto).stream().map(AccountXlsxDto::of).toList();
    }

}