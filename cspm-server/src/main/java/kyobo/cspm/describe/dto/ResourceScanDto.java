package kyobo.cspm.describe.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceScanDto<T> {

    private LocalDateTime scanTime;

    private LocalDateTime resourceCreateTime;

    private String resourceId;

    private String type;

    private Long accountId;

    private String tag;

//    public static <T> ResourceScanDto<T>of(T object){
//        return new ResourceScanDto(
//                object.getScanTime(),
//                object.getResourceCreateTime(),
//                object.getResourceId(),
//                object.getType(),
//                object.getAccountId(),
//                object.getTag()
//        );
//    }
}
