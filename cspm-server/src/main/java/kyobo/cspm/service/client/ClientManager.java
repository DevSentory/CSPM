package kyobo.cspm.service.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientManager {

    @Getter
    private final CurClientInfo curClientInfo;
}