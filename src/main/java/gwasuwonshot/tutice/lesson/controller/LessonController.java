package gwasuwonshot.tutice.lesson.controller;

import com.google.protobuf.Api;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userId.UserId;
import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.lesson.dto.request.CreateLessonRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.CreateLessonResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonByUserResponseDto;
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

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<CreateLessonResponseDto> createLesson(
            @UserId final Long userId,
            @Valid @RequestBody  final CreateLessonRequestDto request ) {

        //레슨정보 생성
        Long createdLessonId = lessonService.createLesson(userId,request);
        //레슨코드 생성
        String createdLessonCode = lessonService.createLessonCode(createdLessonId);

        return ApiResponseDto.success(SuccessStatus.CREATE_LESSON_SUCCESS ,CreateLessonResponseDto.of(createdLessonCode) );

    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonByUserResponseDto> getLessonByUser(@UserId final Long userId){

        return ApiResponseDto.success(SuccessStatus.GET_LESSON_BY_USER_SUCCESS ,
                lessonService.getLessonByUser(userId));


    }

    @GetMapping("/detail/parents/{lessonIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonByUserResponseDto> getLessonDetailByParents(
            @UserId final Long userId
            ,@PathVariable final Long lessonIdx){

        return ApiResponseDto.success(SuccessStatus.GET_LESSON_BY_USER_SUCCESS ,
                lessonService.getLessonByUser(userId));


    }

}
