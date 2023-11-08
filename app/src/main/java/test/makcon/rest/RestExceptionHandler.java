package test.makcon.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import test.makcon.domain.exception.ModelNotExistsException;
import test.makcon.domain.exception.OverlapException;
import test.makcon.domain.exception.VersionOutdatedException;
import test.makcon.dto.ErrorV1;
import test.makcon.dto.constant.ErrorCode;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(OverlapException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorV1 handle(OverlapException exception) {
        return ErrorV1.builder()
                .code(ErrorCode.PERIOD_OVERLAP)
                .message("Provided dates are busy")
                .build();
    }

    @ExceptionHandler(VersionOutdatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorV1 handle(VersionOutdatedException exception) {
        return ErrorV1.builder()
                .code(ErrorCode.VERSION_OUTDATED)
                .message(String.format("Version is incorrect, current value: %s", exception.getVersion()))
                .build();
    }

    @ExceptionHandler(ModelNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorV1 handle(ModelNotExistsException exception) {
        return ErrorV1.builder()
                .code(ErrorCode.MODEL_NOT_EXISTS)
                .message(String.format("The model '%s' not exists", exception.getModel()))
                .build();
    }
}
