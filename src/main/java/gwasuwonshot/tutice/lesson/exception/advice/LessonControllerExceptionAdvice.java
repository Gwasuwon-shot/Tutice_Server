package gwasuwonshot.tutice.lesson.exception.advice;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.lesson.controller.LessonController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import gwasuwonshot.tutice.common.dto.ApiResponseDto;

@RestControllerAdvice(assignableTypes = {LessonController.class})
public class LessonControllerExceptionAdvice {



    /**
     * Tutice Custom  Error : 그중 Lesson 관련 에러
     */
    @ExceptionHandler(/* lesson custom 에러 */)
    protected ResponseEntity<ApiResponseDto> handleLessonCustomException(BasicException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponseDto.error(e.getErrorStatus(), e.getMessage()));
    }
}