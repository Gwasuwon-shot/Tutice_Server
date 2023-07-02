package gwasuwonshot.tutice.lesson.exception.advice;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.lesson.controller.LessonController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

import java.util.Objects;

@RestControllerAdvice(assignableTypes = {LessonController.class})
public class LessonControllerExceptionAdvice {



    /**
     * Tutice Custom  Error : 그중 Lesson 관련 에러
     */
    @ExceptionHandler(/* lesson custom 에러 */)
    protected ResponseEntity<ApiResponse> handleLessonCustomException(BasicException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponse.error(e.getErrorStatus(), e.getMessage()));
    }
}