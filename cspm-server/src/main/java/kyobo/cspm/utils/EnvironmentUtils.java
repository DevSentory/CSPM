package kyobo.cspm.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class EnvironmentUtils {

    private final Environment env;

    public boolean isDevProfileActive() {
        return Arrays.stream(env.getActiveProfiles()).anyMatch("dev"::equals);
    }
}