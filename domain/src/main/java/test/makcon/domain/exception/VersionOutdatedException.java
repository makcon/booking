package test.makcon.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VersionOutdatedException extends RuntimeException {
    private final String model;
    private final Long version;
}
