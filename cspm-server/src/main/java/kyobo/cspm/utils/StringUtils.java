package kyobo.cspm.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class StringUtils {

    public static String localTimeToFormat(String format) {
        try {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}