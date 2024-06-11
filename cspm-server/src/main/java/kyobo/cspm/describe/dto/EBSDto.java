package kyobo.cspm.describe.dto;

import kyobo.cspm.describe.entity.describe.EBSEntity;
import kyobo.cspm.describe.entity.describe.InstanceEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EBSDto {
    private String ebsid;
    private String scanTime;
    private String attachments;
    private String availabilityZone;
    private String createTime;
    private Boolean encrypted;
    private Integer size;
    private String SnapshotId;
    private String state;
    private Integer iops;
    private String volumeType;
    private Boolean multiAttachEnabled;
    private Integer throughput;
    private String instanceId;

    public static EBSDto of(EBSEntity EBSEntity) {
        return new EBSDto(
                EBSEntity.getEbsId(),
                EBSEntity.getScanTime(),
                EBSEntity.getAttachments(),
                EBSEntity.getAvailabilityZone(),
                EBSEntity.getCreateTime(),
                EBSEntity.getEncrypted(),
                EBSEntity.getSize(),
                EBSEntity.getSnapshotId(),
                EBSEntity.getState(),
                EBSEntity.getIops(),
                EBSEntity.getVolumeType(),
                EBSEntity.getMultiAttachEnabled(),
                EBSEntity.getThroughput(),
                Optional.ofNullable(EBSEntity.getInstanceEntity())
                        .map(InstanceEntity::getInstanceId)
                        .orElse(null)
        );
    }
}
