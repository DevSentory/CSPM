package kyobo.cspm.service;

import kyobo.cspm.common.BaseResponse;
import kyobo.cspm.common.BaseResponseStatus;
import kyobo.cspm.describe.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class DetailService {

    private final DescribeRepository describeRepository;

    /** controller를 통해 받은 resourceId를 service에서 분기 처리
     * 발생할 수 있는 예외 상황 2가지
     * 1. switch문에 존재하지 않는 자원의 id
     * 2. database에서 id를 통해 자원을 꺼내올 때 존재 하지 않는 id에 대한 접근
     * NoSuchElementException -> 존재하지 않는 resourceId를 database에서 꺼내려 할 때
     * @param resourceId
     * @return 반환하는 Json
     */
    public BaseResponse<String> findDetailResource(String resourceId, String scanTime, String accountId, String accountName) {
        Optional<List<String>> resourceJson = describeRepository.findResourceJson(resourceId, scanTime, accountId, accountName);
        if (resourceJson.isEmpty()) {
            return BaseResponse.nonVoidError(BaseResponseStatus.EMPTY_RESOURCE);
        }
        return BaseResponse.success(resourceJson.get().get(0));
    }

}