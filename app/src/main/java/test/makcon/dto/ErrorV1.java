package test.makcon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorV1 {
    private final String code;
    private final String message;
    @Builder.Default
    private final Map<String, String> attributes = Map.of();
}
