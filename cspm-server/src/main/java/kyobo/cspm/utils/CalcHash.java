package kyobo.cspm.utils;

import com.google.common.hash.Hashing;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalcHash {

    /**
     * task   : Entity 클래스를 해시 값으로 계산
     * return : sha256 해시 함수가 적용된 문자열 반환
     */
    public static String EntityConvertToHashString(Object entity, String... excludeFields) {
        // excludeFields : Hash값에서 제외될 필드명
        // - Time같은 실시간으로 값이 변하는 필드를 제거할 때 사용한다.

        StringBuilder data = new StringBuilder();

        /*
         * 리플렉션(Reflection) 사용할 때 고려할 점
         * 1. 직접 접근 대비 오버헤드 (= 접근 단계가 더 복잡)
         * 2. 컴파일 타임 최적화의 부재 (= 대부분이 런타임에 수행)
         * 3. 타입 안정성 감소 (= 런타임 에러 발생 확률 증가)
         * 4. 보안 취약점
         */
        // Field : 클래스의 멤버 변수(필드)를 나타내는 클래스
        Field[] arrField = entity.getClass().getDeclaredFields();
        for (Field field : arrField) {
            // 제외할 필드인지 확인
            if (Arrays.stream(excludeFields).noneMatch(field.getName()::equals)) {
                try {
                    // @Getter 함수를 사용하기 위한 getter 함수 생성
                    Method getterMethod = entity.getClass().getMethod("get" +
                            Character.toUpperCase(field.getName().charAt(0)) +
                            field.getName().substring(1));

                    // entity 객체에서 field 값을 가져온다.
                    Object value = getterMethod.invoke(entity);

                    if (null != value) {
                        data.append(value.toString());
                    }
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    /*
                     * IllegalAccessException 발생 상황
                     * 1. 비공개 필드 접근 - getterMethod 처리
                     * 2. final 필드 수정 시도 - 수정 코드가 없음 !
                     * 3. 컨텍스트에서의 보안 제약 - ...? 우리가 다룰 부분이 아닌 것 같음
                     */
                    throw new RuntimeException("필드 값에 접근하는 동안 오류가 발생했습니다", e);
                }
            }
        }

        return data.toString().isEmpty() ? null :
                Hashing.sha256()
                        .hashString(data.toString(), StandardCharsets.UTF_8)
                        .toString();
    }
}