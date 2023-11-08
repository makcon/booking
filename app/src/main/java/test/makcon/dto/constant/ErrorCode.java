package test.makcon.dto.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCode {

    public static final String PERIOD_OVERLAP = "periodOverlap";
    public static final String VERSION_OUTDATED = "versionOutdated";
    public static final String MODEL_NOT_EXISTS = "modelNotExists";
}
