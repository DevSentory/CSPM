package kyobo.cspm.describe.dto;


import kyobo.cspm.describe.entity.describe.RouteEntity;
import kyobo.cspm.describe.entity.describe.VpcEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class RouteDto {
    private String routeId;
    private String scanTime;
    private String associations;
    private String propagatingVgws;
    private String routes;
    private String tags;
    private String ownerId;
    private String vpcId;


    public static RouteDto of(RouteEntity routeEntity) {
        return new RouteDto(
                routeEntity.getRouteId(),
                routeEntity.getScanTime(),
                routeEntity.getAssociations(),
                routeEntity.getPropagatingVgws(),
                routeEntity.getRoutes(),
                routeEntity.getTags(),
                routeEntity.getOwnerId(),
                Optional.ofNullable(routeEntity.getVpcEntity())
                        .map(VpcEntity::getVpcId)
                        .orElse(null)
        );
    }
}
