package kyobo.cspm.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static kyobo.cspm.common.BaseResponseStatus.SUCCESS;
import static kyobo.cspm.common.BaseResponseStatus.SUCCESS_WITH_DATA;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    private final int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우 : 반환데이터 없음
    private BaseResponse() {
        this.isSuccess = SUCCESS_WITH_DATA.isSuccess();
        this.message = SUCCESS_WITH_DATA.getMessage();
        this.code = SUCCESS_WITH_DATA.getCode();
        this.result = null;
    }

    // 요청에 성공한 경우 : 반환데이터 존재
    private BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }

    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    public static BaseResponse<Void> success() {
        return new BaseResponse<>();
    }

    public static <T> BaseResponse<T> success(T result) {
        return new BaseResponse<>(result);
    }

    public static BaseResponse<Void> error(BaseResponseStatus status) {
        return new BaseResponse<>(status);
    }

    public static <T> BaseResponse<T> nonVoidError(BaseResponseStatus status) {
        return new BaseResponse<>(status);
    }
}