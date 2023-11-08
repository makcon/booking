package test.makcon.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ModelNotExistsException extends RuntimeException {
    private final String model;
}
