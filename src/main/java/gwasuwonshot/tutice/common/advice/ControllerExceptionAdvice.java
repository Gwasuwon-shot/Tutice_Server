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
    @ExceptionHandler(MethodArgumentNotValidException.class) //@Vlid에서 걸러지는 에러들
    protected ApiResponseDto handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        return ApiResponseDto.error(ErrorStatus.REQUEST_VALIDATION_EXCEPTION, String.format("%s", fieldError.getDefaultMessage()));
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
    protected ResponseEntity<ApiResponseDto> handleBasicException(BasicException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponseDto.error(e.getErrorStatus(), e.getMessage()));
    }



}