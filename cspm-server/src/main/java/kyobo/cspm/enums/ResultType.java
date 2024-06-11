package kyobo.cspm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultType {
    NONE,
    SUCCESS,
    FAIL_INIT,
    FAIL_EXCEPTION,
    FAIL_DATABASE_INSERT,           // 데이터베이스에 등록(추가) 실패
    FAIL_DUPLICATE_ACCOUNT_ID,      // 중복된 AccountID

    // Describe
    FAIL_AUTH_FAILURE,              // 인증 실패
    FAIL_ACCESS_DENIED,             // 액세스 거부
    FAIL_ACCESS_DENIED_EXCEPTION,   // 액세스 거부 예외
    FAIL_UNAUTHORIZED_OPERATION     // 승인되지 않은 작업
}