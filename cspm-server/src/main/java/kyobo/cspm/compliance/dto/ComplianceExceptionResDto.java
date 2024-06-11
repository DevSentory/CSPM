package kyobo.cspm.compliance.dto;


import kyobo.cspm.compliance.entity.ComplianceExceptionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ComplianceExceptionResDto {

    private Long id;
    private String exceptionTime;
    private String resourceId;
    private String exceptionContent;
    private String exceptionHandler;

    public static ComplianceExceptionResDto of(ComplianceExceptionEntity complianceExceptionEntity) {
        return new ComplianceExceptionResDto(
                complianceExceptionEntity.getId(),
                complianceExceptionEntity.getExceptionTime(),
                complianceExceptionEntity.getResourceId(),
                complianceExceptionEntity.getExceptionContent(),
                complianceExceptionEntity.getExceptionHandler()
        );
    }
}
