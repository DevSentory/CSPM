package kyobo.cspm.service.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CurClientInfo {

    private String client;
    private String accountId;
    private String accountName;

    // 현재 클라이언트에 필요한 데이터가 있으면 추가하여 사용하세요.
}