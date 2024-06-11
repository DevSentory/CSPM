package kyobo.cspm.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    SUCCESS(true, 00, "Success"),
    SUCCESS_WITH_DATA(true, 01, "Success with return"),
    FAILURE(false, 99, "Fail"),
    FAILURE_PARTIAL_SUCCESS(false, 99, "Fail - partial success"),

    /**
     * Client Side
     */
    BAD_REQUEST(false, 01, "잘못된 요청입니다."),
    BAD_Id(false, 02, "Bad Id, Id 값이 존재하지 않습니다."),
    ARGUMENT_NOT_VALID(false, 03, "유효성 검증에 실패하였습니다."),
    ACCOUNT_NAME_DUPLICATED(false, 04, "AccountName이 중복되었습니다."),
    JWT_NOT_VALID(false, 05, "토큰 검증에 실패하였습니다."),

    /**
     * Server Side
     */
    NOT_IMPLEMENTED(false, 11, "Not Implemented, 해당 기능을 지원하지 않습니다."),
    EMPTY_RESOURCE(false, 12, "Result Empty, 값이 비어있습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;
}