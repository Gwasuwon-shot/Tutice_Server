package gwasuwonshot.tutice.common.advice;

import gwasuwonshot.tutice.common.exception.BasicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 400 BAD_REQUEST
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponseDto handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        return ApiResponseDto.error(ErrorStatus.REQUEST_VALIDATION_EXCEPTION, String.format("%s. (%s)", fieldError.getDefaultMessage(), fieldError.getField()));
    }

    /**
     * 500 Internal Server
     * 근데 이거로 처리하면 에러메시지가 로그에 안나타나고 그냥 500으로 퉁쳐지는듯
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ApiResponseDto<Object> handleException(final Exception e) {
        System.out.println(e.getMessage()); //에러 메시지를 로그에 넣기위해
        return ApiResponseDto.error(ErrorStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Tutice Custom  Error
     */
    @ExceptionHandler(BasicException.class)
    protected ResponseEntity<ApiResponseDto> handleBasicException(BasicException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponseDto.error(e.getErrorStatus(), e.getMessage()));
    }
}