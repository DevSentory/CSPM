package kyobo.cspm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info);
    }

    Info info = new Info().title("교보 CSPM-SERVER API 입니다.").version("1.0.0").description(
            "<h3>작성 ASAC 4기수</h3><br>" +
                    "CSPM-SERVER API Black End 작성자<br>" +
                    "이승주: dltmdwn0111@gmail.com<br>" +
                    "김동규: dev.sentory1989@gmail.com<br>" +
                    "이루다: ruda991@gmail.com<br>" +
                    "정승근: wjd15sheep@gmail.com<br>" +
                    "윤승한: yunsh0712@gmail.com<br>");
}