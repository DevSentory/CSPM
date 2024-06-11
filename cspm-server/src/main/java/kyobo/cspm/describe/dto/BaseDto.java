package kyobo.cspm.describe.dto;

import kyobo.cspm.enums.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {
    private String resultType = ResultType.NONE.name();
    private String message = "";

    // 클라이언트로 전달할 데이터가 더 필요할 경우 추가해서 사용

    public BaseDto success() {
        resultType = ResultType.SUCCESS.name();
        return this;
    }

    /**
     * task : Dto 반환에 실패했을 때 실패 타입과 메시지만 요청측으로 반환한다.
     * comment : 자식 Dto는 반환될 필요가 없어서 새로운 객체를 만들어서 반환하는 형태로 구현
     */
    public static BaseDto failed(ResultType resultType, String message) {
        return new BaseDto(resultType.name(), message);
    }
}