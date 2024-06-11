package kyobo.cspm.describe.dto;

import kyobo.cspm.describe.entity.describe.IGWEntity;
import kyobo.cspm.describe.entity.describe.VpcEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IGWDto {
    private String igwId;
    private String scanTime;
    private String attachments;
    private String ownerId;
    private String tags;
    private String vpcId;

    public static IGWDto of(IGWEntity IGWEntity) {
        return new IGWDto(
                IGWEntity.getIgwId(),
                IGWEntity.getScanTime(),
                IGWEntity.getAttachments(),
                IGWEntity.getOwnerId(),
                IGWEntity.getTags(),
                Optional.ofNullable(IGWEntity.getVpcEntity())
                        .map(VpcEntity::getVpcId)
                        .orElse(null)
        );
    }
}
