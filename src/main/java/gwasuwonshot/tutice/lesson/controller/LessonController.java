package gwasuwonshot.tutice.lesson.controller;

import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userId.UserId;
import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.lesson.dto.request.CreateLessonRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.CreateLessonResponseDto;
import gwasuwonshot.tutice.lesson.service.LessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lesson")
@Tag(name = "Lesson", description = "수업 API Document")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<CreateLessonResponseDto> createLesosn(
            @UserId final Long userId,
            @RequestBody @Valid final CreateLessonRequestDto request ) {

        Long createdLessonId = lessonService.createLesson(userId,request);

        String createdLessonCode = lessonService.createLessonCode(createdLessonId);

        return ApiResponseDto.success(SuccessStatus.CREATE_LESSON_SUCCESS ,CreateLessonResponseDto.of(createdLessonCode) );

    }

}
