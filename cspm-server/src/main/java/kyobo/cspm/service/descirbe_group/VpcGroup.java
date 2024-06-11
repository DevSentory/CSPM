package kyobo.cspm.service.descirbe_group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kyobo.cspm.describe.dto.DescribeAccount;
import kyobo.cspm.describe.entity.DescribeEntity;
import kyobo.cspm.describe.entity.describe.*;
import kyobo.cspm.describe.repository.*;
import kyobo.cspm.enums.DescribeType;
import kyobo.cspm.service.aws.CredentialsManager;
import kyobo.cspm.service.components.DescribeSaveComponent;
import kyobo.cspm.utils.StringUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VpcGroup {

    private final DescribeSaveComponent describeSaveComponent;
    private final VpcRepository vpcRepository;
    private final SubnetRepository subnetRepository;
    private final SecurityGroupRepository securityGroupRepository;
    private final RouteRepository routeRepository;
    private final IGWRepository igwRepository;

    private final CredentialsManager credentialsManager;
    private final DescribeType.Group serviceGroup = DescribeType.VPC.getGroup();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Setter
    private DescribeAccount describeAccount;

    @Getter
    List<DescribeEntity> describeEntityList = new ArrayList<>();

    public List<DescribeEntity> vpcDescribe() {
        Ec2Client ec2Client = credentialsManager.getEc2Client();
        List<VpcEntity> vpcEntityList = new ArrayList<>();

        // VPC 자원 스캔
        vpcRepository.deleteAll();
        DescribeVpcsResponse response = ec2Client.describeVpcs();

        // VPC 자원을 순회하면서 정보를 가져온다.
        for (Vpc vpc : response.vpcs()) {
            VpcEntity vpcEntity = new VpcEntity();
            vpcEntity.setScanTime(StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm"));
            vpcEntity.setVpcId(vpc.vpcId());
            vpcEntity.setOwnerId(vpc.ownerId());
            Optional.ofNullable(vpc.state()).map(Object::toString).ifPresent(vpcEntity::setState);
            vpcEntity.setCidrBlock(vpc.cidrBlock());
            vpcEntity.setDhcpOptionsId(vpc.dhcpOptionsId());
            Optional.ofNullable(vpc.instanceTenancy()).map(Object::toString).ifPresent(vpcEntity::setInstanceTenancy);
            vpcEntity.setIsDefault(vpc.isDefault());
            Optional.ofNullable(vpc.tags()).map(Object::toString).ifPresent(vpcEntity::setTags);
            Optional.ofNullable(vpc.cidrBlockAssociationSet()).map(Object::toString).ifPresent(vpcEntity::setCidrBlockAssociationSet);

            if (!CollectionUtils.isEmpty(vpc.cidrBlockAssociationSet())) {
                for (VpcCidrBlockAssociation cidrBlockAssociation : vpc.cidrBlockAssociationSet()) {
                    vpcEntity.setCidrBlockAssociationSetAssociationId(cidrBlockAssociation.associationId());
                    vpcEntity.setCidrBlockAssociationCidrBlock(cidrBlockAssociation.cidrBlock());
                    vpcEntity.setCidrBlockAssociationCidrBlockState(cidrBlockAssociation.cidrBlockState().toString());
                }
            }

            // 'resource_vpc' 테이블 저장용 리스트
            vpcEntityList.add(vpcEntity);
            try {
                String vpcEntityJson = objectMapper.writeValueAsString(vpcEntity);

                // 'resources_describe' 테이블 저장용 리스트
                describeEntityList.add(DescribeEntity.newEntityDescribe(
                        "-", serviceGroup.name(), vpcEntity.getVpcId(), vpcEntity.getTags(), vpcEntityJson
                ));
            }catch (JsonProcessingException e) {
                e.fillInStackTrace();
            }
        }

        // ★ resource_instance 리스트를 InstanceRepository에 저장하기 위해 세트로 묶어서 저장
        describeSaveComponent.saveAwsResources(DescribeType.VPC, vpcEntityList, vpcRepository);
        return describeEntityList;
    }

    public List<DescribeEntity> subnetDescribe() {
        Ec2Client ec2Client = credentialsManager.getEc2Client();
        List<SubnetEntity> subnetEntityList = new ArrayList<>();

        // Subnet 자원 스캔
        subnetRepository.deleteAll();
        DescribeSubnetsResponse response = ec2Client.describeSubnets();

        // Subnet 자원을 순회하면서 정보를 가져온다.
        for (Subnet subnet : response.subnets()) {
            SubnetEntity subnetEntity = new SubnetEntity();
            subnetEntity.setScanTime(StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm"));
            subnetEntity.setSubnetId(subnet.subnetId());
            subnetEntity.setAvailabilityZone(subnet.availabilityZone());
            subnetEntity.setAvailabilityZoneId(subnet.availabilityZoneId());
            subnetEntity.setAvailableIpAddressCount(subnet.availableIpAddressCount());
            subnetEntity.setCidrBlock(subnet.cidrBlock());
            subnetEntity.setDefaultForAz(subnet.defaultForAz());
            subnetEntity.setMapPublicIpOnLaunch(subnet.mapPublicIpOnLaunch());
            subnetEntity.setMapCustomerOwnedIpOnLaunch(subnet.mapCustomerOwnedIpOnLaunch());
            Optional.ofNullable(subnet.state()).map(Object::toString).ifPresent(subnetEntity::setState);
            subnetEntity.setOwnerId(subnet.ownerId());
            subnetEntity.setAssignIpv6AddressOnCreation(subnet.assignIpv6AddressOnCreation());
            Optional.ofNullable(subnet.ipv6CidrBlockAssociationSet()).map(Object::toString).ifPresent(subnetEntity::setIpv6CidrBlockAssociationSet);
            subnetEntity.setSubnetArn(subnet.subnetArn());
            subnetEntity.setEnableDns64(subnet.enableDns64());
            subnetEntity.setIpv6Native(subnet.ipv6Native());
            Optional.ofNullable(subnet.privateDnsNameOptionsOnLaunch()).map(Object::toString).ifPresent(subnetEntity::setPrivateDnsNameOptionsOnLaunch);
            subnetEntity.setVpcId(subnet.vpcId());


            // 'resource_subnet' 테이블 저장용 리스트
            subnetEntityList.add(subnetEntity);

            try {
                String subnetEntityJson = objectMapper.writeValueAsString(subnetEntity);
                // 'resources_describe' 테이블 저장용 리스트
                describeEntityList.add(DescribeEntity.newEntityDescribe(
                        "-", serviceGroup.name(), subnetEntity.getSubnetId(), "-", subnetEntityJson
                ));
            }catch (JsonProcessingException e) {
                e.fillInStackTrace();
            }
        }

        // resource_instance 리스트를 InstanceRepository에 저장하기 위해 세트로 묶어서 저장
        describeSaveComponent.saveAwsResources(DescribeType.SUBNET, subnetEntityList, subnetRepository);
        return describeEntityList;
    }

    public List<DescribeEntity> securityGroupDescribe() {
        Ec2Client ec2Client = credentialsManager.getEc2Client();
        List<SecurityGroupEntity> securityGroupEntityList = new ArrayList<>();

        // SecurityGroup 자원 스캔
        securityGroupRepository.deleteAll();
        DescribeSecurityGroupsResponse response = ec2Client.describeSecurityGroups();

        // SecurityGroup 자원을 순회하면서 정보를 가져온다.
        for (SecurityGroup sg : response.securityGroups()) {
            SecurityGroupEntity securityGroupEntity = new SecurityGroupEntity();
            securityGroupEntity.setScanTime(StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm"));
            securityGroupEntity.setGroupId(sg.groupId());
            securityGroupEntity.setDescription(sg.description());
            securityGroupEntity.setGroupName(sg.groupName());
            Optional.ofNullable(sg.ipPermissions()).map(Object::toString).ifPresent(securityGroupEntity::setIpPermissions);
            securityGroupEntity.setOwnerId(sg.ownerId());
            Optional.ofNullable(sg.ipPermissionsEgress()).map(Object::toString).ifPresent(securityGroupEntity::setIpPermissionsEgress);

            // 'resource_security_group' 테이블 저장용 리스트
            securityGroupEntityList.add(securityGroupEntity);

            try {
                String securityGroupEntityJson =  objectMapper.writeValueAsString(securityGroupEntity);

                // 'resources_describe' 테이블 저장용 리스트
                describeEntityList.add(DescribeEntity.newEntityDescribe(
                         securityGroupEntity.getDescription(), serviceGroup.name(), securityGroupEntity.getGroupId(), "-", securityGroupEntityJson
                ));
            }catch (JsonProcessingException e){
                e.fillInStackTrace();
            }
        }

        // resource_instance 리스트를 InstanceRepository에 저장하기 위해 세트로 묶어서 저장
        describeSaveComponent.saveAwsResources(DescribeType.SECURITY_GROUP, securityGroupEntityList, securityGroupRepository);
        return describeEntityList;
    }

    public List<DescribeEntity> routeDescribe() {
        Ec2Client ec2Client = credentialsManager.getEc2Client();
        List<RouteEntity> routeEntityList = new ArrayList<>();

        // Routing 자원 스캔
        routeRepository.deleteAll();
        DescribeRouteTablesResponse response = ec2Client.describeRouteTables();

        // Routing 자원을 순회하면서 정보를 가져온다.
        for (RouteTable routeTable : response.routeTables()) {
            RouteEntity routeEntity = new RouteEntity();
            routeEntity.setRouteId(routeTable.routeTableId());
            routeEntity.setScanTime(StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm"));
            Optional.ofNullable(routeTable.associations()).map(Object::toString).ifPresent(routeEntity::setAssociations);
            Optional.ofNullable(routeTable.propagatingVgws()).map(Object::toString).ifPresent(routeEntity::setPropagatingVgws);
            Optional.ofNullable(routeTable.routes()).map(Object::toString).ifPresent(routeEntity::setRoutes);
            Optional.ofNullable(routeTable.tags()).map(Object::toString).ifPresent(routeEntity::setTags);
            routeEntity.setOwnerId(routeTable.ownerId());
            routeEntity.setVpcId(routeTable.vpcId());

            // 'resource_route' 테이블 저장용 리스트
            routeEntityList.add(routeEntity);

            try {
                String routeEntityJson = objectMapper.writeValueAsString(routeEntity);


                // 'resources_describe' 테이블 저장용 리스트
                describeEntityList.add(DescribeEntity.newEntityDescribe(
                        "-", serviceGroup.name(), routeEntity.getRouteId(), routeEntity.getTags(), routeEntityJson
                ));
            }catch (JsonProcessingException e){
                e.fillInStackTrace();
            }
        }

        // resource_routing 리스트를 RounteRepository에 저장하기 위해 세트로 묶어서 저장
        describeSaveComponent.saveAwsResources(DescribeType.ROUTE, routeEntityList, routeRepository);
        return describeEntityList;
    }

    public List<DescribeEntity> igwDescribe() {
        Ec2Client ec2Client = credentialsManager.getEc2Client();
        List<IGWEntity> entityIGWList = new ArrayList<>();

        // IGW 자원 스캔
        igwRepository.deleteAll();
        DescribeInternetGatewaysResponse response = ec2Client.describeInternetGateways();

        // IGW 자원을 순회하면서 정보를 가져온다.
        LocalDateTime scanTime = LocalDateTime.now();
        for (InternetGateway igw : response.internetGateways()) {
            IGWEntity igwEntity = new IGWEntity();
            igwEntity.setScanTime(StringUtils.localTimeToFormat("yyyy-MM-dd HH:mm"));
            igwEntity.setIgwId(igw.internetGatewayId());
            igwEntity.setAttachments(igw.attachments().toString());
            Optional.ofNullable(igw.attachments()).map(Object::toString).ifPresent(igwEntity::setAttachments);
            igwEntity.setOwnerId(igw.ownerId());
            igwEntity.setTags(igw.tags().toString());
            Optional.ofNullable(igw.tags()).map(Object::toString).ifPresent(igwEntity::setTags);
            Optional.ofNullable(igw.attachments())
                    .filter(attachments -> !attachments.isEmpty())
                    .ifPresent(attachments -> attachments.forEach(attachment ->
                            igwEntity.setVpcId(attachment.vpcId())
                    ));

            // 'resource_igw' 테이블 저장용 리스트
            entityIGWList.add(igwEntity);

            try {
                String igwEntityJson = objectMapper.writeValueAsString(igwEntity);

                // 'resources_describe' 테이블 저장용 리스트
                describeEntityList.add(DescribeEntity.newEntityDescribe(
                        "-", serviceGroup.name(), igwEntity.getIgwId(), igwEntity.getTags(), igwEntityJson
                ));
            }catch(JsonProcessingException e){
                e.fillInStackTrace();
            }
        }

        // resource_igw 리스트를 IGWRepository 저장하기 위해 세트로 묶어서 저장
        describeSaveComponent.saveAwsResources(DescribeType.IGW, entityIGWList, igwRepository);
        return describeEntityList;
    }
}