package kyobo.cspm;

import jakarta.annotation.PostConstruct;
import kyobo.cspm.utils.AES256;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RequiredArgsConstructor
public class CspmApplication {

    private final AES256 aes256Util;

    public static void main(String[] args) {
        SpringApplication.run(CspmApplication.class, args);
    }

    /**
     * [2024.05.09 작업 완료 - Donggyu]
     * task : AES256 초기화
     */
    @PostConstruct
    private void aes256Init() {
        aes256Util.init();
    }

}