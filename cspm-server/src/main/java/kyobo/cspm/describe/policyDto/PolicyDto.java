package kyobo.cspm.describe.policyDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PolicyDto {
    private String resourceId;

    private String scanTime;

    private Long policyId;

    public PolicyDto(String resourceId, String scanTime, Long policyId) {
        this.resourceId = resourceId;
        this.scanTime = scanTime;
        this.policyId = policyId;
    }
}
