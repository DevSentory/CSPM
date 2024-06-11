package kyobo.cspm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FillDownloadCase {
    ACCOUNT("account"),
    RESOURCE_DESCRIBE("describe"),
    DESCRIIBE_TO_COMPLIANCE("compliance");

    private final String fileName;
}