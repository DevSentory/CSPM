package kyobo.cspm.service.components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Component
public class PageFilterData {
    HashMap<String, List<?>> accountDataMap = new HashMap<>();
    HashMap<String, List<?>> describeDataMap = new HashMap<>();
    HashMap<String, List<?>> complianceMap = new HashMap<>();
}