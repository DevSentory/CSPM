package kyobo.cspm.service.descirbe_group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kyobo.cspm.describe.entity.DescribeEntity;
import kyobo.cspm.describe.entity.describe.EBSEntity;
import kyobo.cspm.describe.entity.describe.ENIEntity;
import kyobo.cspm.describe.entity.describe.InstanceEntity;
import kyobo.cspm.describe.repository.EBSRepository;
import kyobo.cspm.describe.repository.ENIRepository;
import kyobo.cspm.describe.repository.InstanceRepository;
import kyobo.cspm.enums.DescribeType;
import kyobo.cspm.service.aws.CredentialsManager;
import kyobo.cspm.service.components.DescribeSaveComponent;
import kyobo.cspm.utils.StringUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Ec2Group {

    private final DescribeSaveComponent describeSaveComponent;
    private final InstanceRepository instanceRepository;
    private final EBSRepository EBSRepository;
    private final ENIRepository ENIRepository;

    private final CredentialsManager credentialsManager;
    private final DescribeType.Group serviceGroup = DescribeType.INSTANCE.getGroup();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    List<DescribeEntity> describeEntityList = new ArrayList<>();

    /**
     * task : Instance 자원 스캔
     */
    public List<DescribeEntity> instanceDescribe() {
        Ec2Client ec2Client = credentialsManager.getEc2Client();
        List<InstanceEntity> instanceEntityList = new ArrayList<>();

        // Instance 자원 스캔
        instanceRepository.deleteAll();
        DescribeInstancesResponse response = ec2Client.describeInstances(DescribeInstancesRequest.builder().build());
        List<Instance> instances = response.reservations().stream().flatMap(reservation -> reservation.instances().stream()).toList();

        // Instance 자원을 순회하면서 정보를 가져온다.
        for (Instance instance : instances) {
            if (null == instance) continue;

            InstanceEntity instanceEntity = new InstanceEntity();
            instanceEntity.setScanTime(StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm"));
            instanceEntity.setKeyName(instance.keyName());
            instanceEntity.setInstanceId(instance.instanceId());
            instanceEntity.setAmiLaunchIndex(instance.amiLaunchIndex());
            instanceEntity.setImageId(instance.imageId());
            Optional.ofNullable(instance.instanceType()).map(Object::toString).ifPresent(instanceEntity::setInstanceType);
            Optional.ofNullable(instance.launchTime()).map(Object::toString).ifPresent(instanceEntity::setLaunchTime);
            Optional.ofNullable(instance.monitoring()).map(Monitoring::stateAsString).ifPresent(instanceEntity::setMonitoring);
            Optional.ofNullable(instance.placement()).map(Placement::toString).ifPresent(instanceEntity::setPlacement);
            instanceEntity.setPrivateDnsName(instance.privateDnsName());
            instanceEntity.setPrivateIpAddress(instance.privateIpAddress());
            Optional.ofNullable(instance.productCodes()).map(Object::toString).ifPresent(instanceEntity::setProductCodes);
            instanceEntity.setPublicDnsName(instance.publicDnsName());
            instanceEntity.setPublicAddress(instance.publicIpAddress());
            Optional.ofNullable(instance.state()).map(Object::toString).ifPresent(instanceEntity::setState);
            instanceEntity.setStateTransitionReason(instance.stateTransitionReason());
            instanceEntity.setVpcId(instance.vpcId());
            instanceEntity.setArchitecture(instance.architectureAsString());
            Optional.ofNullable(instance.blockDeviceMappings()).map(Object::toString).ifPresent(instanceEntity::setBlockDeviceMappings);
            instanceEntity.setClientToken(instance.clientToken());
            instanceEntity.setEbsOptimized(instance.ebsOptimized());
            instanceEntity.setEnaSupport(instance.enaSupport());
            instanceEntity.setHypervisor(instance.hypervisorAsString());
            Optional.ofNullable(instance.networkInterfaces()).map(Object::toString).ifPresent(instanceEntity::setNetworkInterfaces);
            instanceEntity.setRootDeviceName(instance.rootDeviceName());
            instanceEntity.setRootDeviceType(instance.rootDeviceTypeAsString());
            Optional.ofNullable(instance.securityGroups()).map(Object::toString).ifPresent(instanceEntity::setSecurityGroups);
            instanceEntity.setSourceDestCheck(instance.sourceDestCheck());
            Optional.ofNullable(instance.tags()).map(Object::toString).ifPresent(instanceEntity::setTags);
            instanceEntity.setVirtualizationType(instance.virtualizationTypeAsString());
            Optional.ofNullable(instance.cpuOptions()).map(Object::toString).ifPresent(instanceEntity::setCpuOptions);
            Optional.ofNullable(instance.capacityReservationSpecification()).map(Object::toString).ifPresent(instanceEntity::setCapacityReservationSpecification);
            Optional.ofNullable(instance.hibernationOptions()).map(Object::toString).ifPresent(instanceEntity::setHibernationOptions);
            Optional.ofNullable(instance.metadataOptions()).map(Object::toString).ifPresent(instanceEntity::setMetadataOptions);

            InstanceMetadataOptionsResponse instanceMetadataOptionsResponse = instance.metadataOptions();
            if (instanceMetadataOptionsResponse != null) {
                instanceEntity.setHttpPutResponseHopLimit(instanceMetadataOptionsResponse.httpPutResponseHopLimit());
                Optional.ofNullable(instanceMetadataOptionsResponse.httpEndpoint()).map(Object::toString).ifPresent(instanceEntity::setHttpEndpoint);
                Optional.ofNullable(instanceMetadataOptionsResponse.httpProtocolIpv6()).map(Object::toString).ifPresent(instanceEntity::setHttpProtocolIpv6);
                instanceEntity.setInstanceMetadataTags(instanceMetadataOptionsResponse.instanceMetadataTagsAsString());
            }

            Optional.ofNullable(instance.enclaveOptions()).map(Object::toString).ifPresent(instanceEntity::setEnclaveOptions);
            instanceEntity.setBootMode(instance.bootModeAsString());
            instanceEntity.setUsageOperation(instance.usageOperation());
            instanceEntity.setPlatformDetails(instance.platformDetails());
            Optional.ofNullable(instance.usageOperationUpdateTime()).map(Object::toString).ifPresent(instanceEntity::setUsageOperationUpdateTime);
            Optional.ofNullable(instance.privateDnsNameOptions()).map(Object::toString).ifPresent(instanceEntity::setPrivateDnsNameOptions);
            Optional.ofNullable(instance.maintenanceOptions()).map(Object::toString).ifPresent(instanceEntity::setMaintenanceOptions);
            instanceEntity.setCurrentInstanceBootMode(instance.currentInstanceBootModeAsString());
            instanceEntity.setSubnetId(instance.subnetId());
            Optional.ofNullable(instance.securityGroups())
                    .filter(securityGroups -> !securityGroups.isEmpty())
                    .ifPresent(securityGroups -> securityGroups.forEach(attachment ->
                            instanceEntity.setSecurityGroupId(attachment.groupId())
                    ));

            // 'resource_instance' 테이블 저장용 리스트
            instanceEntityList.add(instanceEntity);

            try {
                // json 생성
                String instanceEntityJson = objectMapper.writeValueAsString(instanceEntity);

                describeEntityList.add(DescribeEntity.newEntityDescribe(
                        instanceEntity.getLaunchTime(), serviceGroup.name(), instanceEntity.getInstanceId()
                        , instanceEntity.getTags(), instanceEntityJson));
            } catch (JsonProcessingException e) {
                e.fillInStackTrace();
            }
        }
        // resource_instance 리스트를 InstanceRepository에 저장하기 위해 세트로 묶어서 저장
        describeSaveComponent.saveAwsResources(DescribeType.INSTANCE, instanceEntityList, instanceRepository);
        return describeEntityList;
    }

    /**
     * task : EBS 자원 스캔
     */
    public List<DescribeEntity> ebsDescribe() {
        Ec2Client ec2Client = credentialsManager.getEc2Client();
        List<EBSEntity> EBSEntityList = new ArrayList<>();

        // EBS 자원 스캔
        EBSRepository.deleteAll();
        DescribeVolumesResponse response = ec2Client.describeVolumes();

        // EBS 자원을 순회하면서 정보를 가져온다.
        for (Volume volume : response.volumes()) {
            EBSEntity EBSEntity = new EBSEntity();
            EBSEntity.setScanTime(StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm"));
            EBSEntity.setEbsId(volume.volumeId());
            Optional.ofNullable(volume.attachments()).map(Object::toString).ifPresent(EBSEntity::setAttachments);
            EBSEntity.setAvailabilityZone(volume.availabilityZone());
            Optional.ofNullable(volume.createTime()).map(Object::toString).ifPresent(EBSEntity::setCreateTime);
            EBSEntity.setEncrypted(volume.encrypted());
            EBSEntity.setSize(volume.size());
            EBSEntity.setSnapshotId(volume.snapshotId());
            EBSEntity.setState(volume.stateAsString());
            EBSEntity.setIops(volume.iops());
            Optional.ofNullable(volume.volumeType()).map(Object::toString).ifPresent(EBSEntity::setVolumeType);
            EBSEntity.setMultiAttachEnabled(volume.multiAttachEnabled());
            EBSEntity.setThroughput(volume.throughput());
            Optional.ofNullable(volume.attachments())
                    .filter(attachments -> !attachments.isEmpty())
                    .ifPresent(attachments -> attachments.forEach(attachment ->
                            EBSEntity.setInstanceId(attachment.instanceId())
                    ));

            // 'resource_ebs' 테이블 저장용 리스트
            EBSEntityList.add(EBSEntity);
            try {
                String EBSEntityJson = objectMapper.writeValueAsString(EBSEntity);

                // 'resources_describe' 테이블 저장용 리스트
                describeEntityList.add(DescribeEntity.newEntityDescribe(
                        EBSEntity.getCreateTime(), serviceGroup.name(), EBSEntity.getEbsId(), "-", EBSEntityJson));

            } catch (JsonProcessingException e) {
                e.fillInStackTrace();
            }
        }

        // resource_ebs 리스트를 EBSRepository에 저장하기 위해 세트로 묶어서 저장
        describeSaveComponent.saveAwsResources(DescribeType.EBS, EBSEntityList, EBSRepository);
        return describeEntityList;
    }

    /**
     * task : ENI 자원 스캔
     */
    public List<DescribeEntity> eniDescribe() {
        Ec2Client ec2Client = credentialsManager.getEc2Client();
        List<ENIEntity> ENIEntityList = new ArrayList<>();

        // ENI 자원 스캔
        ENIRepository.deleteAll();
        DescribeNetworkInterfacesResponse response = ec2Client.describeNetworkInterfaces();

        // ENI 자원을 순회하면서 정보를 가져온다.
        for (NetworkInterface networkInterface : response.networkInterfaces()) {
            ENIEntity ENIEntity = new ENIEntity();
            ENIEntity.setScanTime(StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm"));
            ENIEntity.setEniId(networkInterface.networkInterfaceId());
            Optional.ofNullable(networkInterface.attachment()).map(Object::toString).ifPresent(ENIEntity::setAttachment);
            ENIEntity.setAvailabilityZone(networkInterface.availabilityZone());
            ENIEntity.setDescription(networkInterface.description());
            Optional.ofNullable(networkInterface.groups()).map(Object::toString).ifPresent(ENIEntity::setGroupsgr);
            ENIEntity.setInterfaceType(networkInterface.interfaceTypeAsString());
            ENIEntity.setIpv6Addresses(networkInterface.ipv6Address());
            ENIEntity.setMacAddress(networkInterface.macAddress());
            ENIEntity.setOwnerId(networkInterface.ownerId());
            ENIEntity.setPrivateDnsName(networkInterface.privateDnsName());
            ENIEntity.setPrivateIpAddress(networkInterface.privateIpAddress());
            Optional.ofNullable(networkInterface.privateIpAddresses()).map(Object::toString).ifPresent(ENIEntity::setPrivateIpAddresses);
            ENIEntity.setRequesterManaged(networkInterface.requesterManaged());
            ENIEntity.setSourceDestCheck(networkInterface.sourceDestCheck());
            ENIEntity.setStatus(networkInterface.statusAsString());
            ENIEntity.setSubnetId(networkInterface.subnetId());
            Optional.ofNullable(networkInterface.tagSet()).map(Object::toString).ifPresent(ENIEntity::setTagSet);
            ENIEntity.setVpcId(networkInterface.vpcId());
            Optional.ofNullable(networkInterface.attachment()).map(NetworkInterfaceAttachment::attachTime).map(Object::toString).ifPresent(ENIEntity::setAttachmentTime);
            Optional.ofNullable(networkInterface.attachment())
                    .ifPresent(attachment -> ENIEntity.setInstanceId(attachment.instanceId()));

            // 'resource_eni' 테이블 저장용 리스트
            ENIEntityList.add(ENIEntity);

            try {
                String ENIEntityJson = objectMapper.writeValueAsString(ENIEntity);

                // 'resources_describe' 테이블 저장용 리스트
                describeEntityList.add(DescribeEntity.newEntityDescribe(
                        "-", serviceGroup.name(), ENIEntity.getEniId(), "-", ENIEntityJson));
            } catch (JsonProcessingException e) {
                e.fillInStackTrace();
            }
        }

        // resource_eni 리스트를 ENIRepository에 저장하기 위해 세트로 묶어서 저장
        describeSaveComponent.saveAwsResources(DescribeType.ENI, ENIEntityList, ENIRepository);
        return describeEntityList;
    }
}