package gwasuwonshot.tutice.common.advice;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import gwasuwonshot.tutice.common.exception.BasicException;

import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 400 BAD_REQUEST
     */


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse handleHttpMessageNotReadableException( final HttpMessageNotReadableException e) {
        return ApiResponse.error(ErrorStatus.NOT_READABLE_REQUEST_EXCEPTION, String.format("%s",ErrorStatus.NOT_READABLE_REQUEST_EXCEPTION.getMessage()));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class) //@Vlid에서 걸러지는 에러들
    protected ApiResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        return ApiResponse.error(ErrorStatus.REQUEST_VALIDATION_EXCEPTION, String.format("%s", fieldError.getDefaultMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ApiResponse handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        return ApiResponse.error(ErrorStatus.MISSING_REQUEST_HEADER_EXCEPTION, String.format("%s. (%s)", ErrorStatus.MISSING_REQUEST_HEADER_EXCEPTION.getMessage(), e.getHeaderName()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ApiResponse handleMissingRequestParameterException(final MissingServletRequestParameterException e) {
        return ApiResponse.error(ErrorStatus.MISSING_REQUEST_PARAMETER_EXCEPTION, String.format("%s. (%s)", ErrorStatus.MISSING_REQUEST_PARAMETER_EXCEPTION.getMessage(), e.getParameterName()));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    protected ApiResponse handleMissingRequestParameterException(final ConstraintViolationException e) {
        return ApiResponse.error(ErrorStatus.MISSING_REQUEST_PARAMETER_EXCEPTION, String.format("%s. (%s)", ErrorStatus.MISSING_REQUEST_PARAMETER_EXCEPTION.getMessage(), e.getConstraintViolations()));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    protected ApiResponse handleMissingRequestPathVariableException(final IllegalArgumentException e) {
        return ApiResponse.error(ErrorStatus.MISSING_REQUEST_PATH_VARIABLE_EXCEPTION, String.format("%s. (%s)", ErrorStatus.MISSING_REQUEST_PATH_VARIABLE_EXCEPTION.getMessage(), e.getMessage()));
    }


    /**
     * 500 Internal Server
     * 근데 이거로 처리하면 에러메시지가 로그에 안나타나고 그냥 500으로 퉁쳐지는듯
     */
    // TODO 추후 500에러 잘되게끔.... 커스텀해보기 로그에 에러가 찍히게끔
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    protected ApiResponse<Object> handleException(final Exception e) {
//        System.out.println(e.getMessage()); //에러 메시지를 로그에 넣기위해
//        return ApiResponse.error(ErrorStatus.INTERNAL_SERVER_ERROR);
//    }

    /**
     * Tutice Custom  Error
     */
    @ExceptionHandler(BasicException.class)
    protected ResponseEntity<ApiResponse> handleBasicException(BasicException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponse.error(e.getErrorStatus(), e.getMessage()));
    }



}