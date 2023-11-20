package gwasuwonshot.tutice.lesson.controller;

import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.lesson.dto.request.CreateLessonMaintenanceRequestDto;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequestDto;
import gwasuwonshot.tutice.lesson.dto.request.UpdateLessonParentsRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.CreateLessonResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonExistenceByUserResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonDetailResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonProgressResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByParents.GetLessonByParentsResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher.GetLessonByTeacherResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getLessonRegularSchedule.GetLessonRegularScheduleResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenance.GetMissingMaintenanceLessonResponseDto;
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
            @UserIdx final Long userIdx,
            @Valid @RequestBody final CreateLessonRequestDto request) {
        return ApiResponseDto.success(SuccessStatus.CREATE_LESSON_SUCCESS, lessonService.createLesson(userIdx, request));
    }


    @PostMapping("/maintenance")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto createLessonMaintenance(
            @UserIdx final Long userIdx,
            @Valid @RequestBody final CreateLessonMaintenanceRequestDto request) {

        if (lessonService.createLessonMaintenance(userIdx, request.getLessonIdx(), request.getIsLessonMaintenance())) {
            return ApiResponseDto.success(SuccessStatus.AUTO_CREATE_SCHEDULE_FROM_LESSON_MAINTENANCE_SUCCESS);

        }
        return ApiResponseDto.success(SuccessStatus.FINISH_LESSON_SUCCESS);
    }


    @GetMapping("/existence")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonExistenceByUserResponseDto> getLessonExistenceByUser(@UserIdx final Long userIdx) {

        return ApiResponseDto.success(SuccessStatus.GET_LESSON_EXISTENCE_BY_USER_SUCCESS,
                lessonService.getLessonExistenceByUser(userIdx));
    }

    @GetMapping("/missing-maintenance/existence")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<Boolean> getMissingMaintenanceExistenceByTeacher(@UserIdx final Long userIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_MISSING_MAINTENANCE_EXIST_BY_TEACHER_SUCCESS,
                lessonService.getMissingMaintenanceExistenceByTeacher(userIdx));
    }


    @GetMapping("/teacher")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonByTeacherResponseDto> getLessonByTeacher(@UserIdx final Long userIdx) {

        return ApiResponseDto.success(SuccessStatus.GET_LESSON_BY_TEACHER_SUCCESS,
                GetLessonByTeacherResponseDto.of(lessonService.getLessonByTeacher(userIdx))
        );
    }


    @GetMapping("/parents")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonByParentsResponseDto> getLessonByParents(@UserIdx final Long userIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_LESSON_BY_PARENTS_SUCCESS,
                GetLessonByParentsResponseDto.of(lessonService.getLessonByParents(userIdx))
        );
    }


    @GetMapping("/missing-maintenance")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetMissingMaintenanceLessonResponseDto> getMissingMaintenanceLessonByTeacher(@UserIdx final Long userIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_MISSING_MAINTENANCE_LESSON_BY_TEACHER_SUCCESS,
                GetMissingMaintenanceLessonResponseDto.of(lessonService.getMissingMaintenanceLessonByTeacher(userIdx))
        );
    }


    @GetMapping("/{lessonIdx}/regular-schedule")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonRegularScheduleResponseDto> getLessonRegularSchedule(@UserIdx final Long userIdx,
                                                                                        @PathVariable final Long lessonIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_LESSON_REGULAR_SCHEDULE_SUCCESS, lessonService.getLessonRegularSchedule(userIdx, lessonIdx));
    }

    @GetMapping("/{lessonIdx}/progress")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonProgressResponseDto> getLessonProgress(@UserIdx final Long userIdx,
                                                                          @PathVariable final Long lessonIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_LESSON_PROGRESS_SUCCESS, lessonService.getLessonProgress(userIdx, lessonIdx));
    }

    @GetMapping("/{lessonIdx}/detail")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonDetailResponseDto> getLessonDetail(@UserIdx final Long userIdx,
                                                                      @PathVariable final Long lessonIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_LESSON_DETAIL_SUCCESS, lessonService.getLessonDetail(userIdx, lessonIdx));
    }


    @PatchMapping("/parents")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateLessonParents(
            @UserIdx final Long userIdx,
            @Valid @RequestBody final UpdateLessonParentsRequestDto request) {

        lessonService.updateLessonParents(userIdx, request.getLessonCode());
        return ApiResponseDto.success(SuccessStatus.UPDATE_LESSON_PARENTS_SUCCESS);

    }


    @DeleteMapping("/{lessonIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteLesson(@UserIdx final Long userIdx,
                                       @PathVariable final Long lessonIdx) {
        lessonService.deleteLesson(userIdx, lessonIdx);

        return ApiResponseDto.success(SuccessStatus.DELETE_LESSON_SUCCESS);
    }


}

