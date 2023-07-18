package gwasuwonshot.tutice.user.exception.advice;

import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.user.controller.UserController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {UserController.class})
public class UserControllerExceptionAdvice {
    /**
     * Tutice Custom  Error : 그중 Lesson 관련 에러
     */
    @ExceptionHandler(/* lesson custom 에러 */)
    protected ResponseEntity<ApiResponseDto> handleLessonCustomException(BasicException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponseDto.error(e.getErrorStatus(), e.getMessage()));
    }
}
