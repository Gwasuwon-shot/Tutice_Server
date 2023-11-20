package gwasuwonshot.tutice.lesson.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.lesson.dto.request.CreateLessonMaintenanceRequest;
import gwasuwonshot.tutice.lesson.dto.request.UpdateLessonParentsRequest;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequest;
import gwasuwonshot.tutice.lesson.dto.response.CreateLessonResponse;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonDetailResponse;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonExistenceByUserResponse;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonProgressResponse;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByParents.GetLessonByParentsResponse;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher.GetLessonByTeacherResponse;
import gwasuwonshot.tutice.lesson.dto.response.getLessonRegularSchedule.GetLessonRegularScheduleResponse;
import gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenance.GetMissingMaintenanceLessonByTeacherResponse;
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
    public ApiResponse<CreateLessonResponse> createLesson(
            @UserIdx final Long userIdx,
            @Valid @RequestBody final CreateLessonRequest request) {
        return ApiResponse.success(SuccessStatus.CREATE_LESSON_SUCCESS,
                lessonService.createLesson(userIdx, request));
    }


    @PostMapping("/maintenance")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createLessonMaintenance(
            @UserIdx final Long userIdx,
            @Valid @RequestBody final CreateLessonMaintenanceRequest request) {

        if (lessonService.createLessonMaintenance(userIdx, request.getLessonIdx(), request.getIsLessonMaintenance())) {
            return ApiResponse.success(SuccessStatus.AUTO_CREATE_SCHEDULE_FROM_LESSON_MAINTENANCE_SUCCESS);

        }
        return ApiResponse.success(SuccessStatus.FINISH_LESSON_SUCCESS);
    }


    @GetMapping("/existence")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetLessonExistenceByUserResponse> getLessonExistenceByUser(@UserIdx final Long userIdx) {

        return ApiResponse.success(SuccessStatus.GET_LESSON_EXISTENCE_BY_USER_SUCCESS,
                lessonService.getLessonExistenceByUser(userIdx));
    }

    @GetMapping("/missing-maintenance/existence")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Boolean> getMissingMaintenanceExistenceByTeacher(@UserIdx final Long userIdx) {
        return ApiResponse.success(SuccessStatus.GET_MISSING_MAINTENANCE_EXIST_BY_TEACHER_SUCCESS,
                lessonService.getMissingMaintenanceExistenceByTeacher(userIdx));
    }


    @GetMapping("/teacher")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetLessonByTeacherResponse> getLessonByTeacher(@UserIdx final Long userIdx) {

        return ApiResponse.success(SuccessStatus.GET_LESSON_BY_TEACHER_SUCCESS,
                GetLessonByTeacherResponse.of(lessonService.getLessonByTeacher(userIdx))
        );
    }


    @GetMapping("/parents")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetLessonByParentsResponse> getLessonByParents(@UserIdx final Long userIdx) {
        return ApiResponse.success(SuccessStatus.GET_LESSON_BY_PARENTS_SUCCESS,
                GetLessonByParentsResponse.of(lessonService.getLessonByParents(userIdx))
        );
    }


    @GetMapping("/missing-maintenance")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetMissingMaintenanceLessonByTeacherResponse> getMissingMaintenanceLessonByTeacher(@UserIdx final Long userIdx) {
        return ApiResponse.success(SuccessStatus.GET_MISSING_MAINTENANCE_LESSON_BY_TEACHER_SUCCESS,
                GetMissingMaintenanceLessonByTeacherResponse.of(lessonService.getMissingMaintenanceLessonByTeacher(userIdx))
        );
    }


    @GetMapping("/{lessonIdx}/regular-schedule")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetLessonRegularScheduleResponse> getLessonRegularSchedule(@UserIdx final Long userIdx,
                                                                                  @PathVariable final Long lessonIdx) {
        return ApiResponse.success(SuccessStatus.GET_LESSON_REGULAR_SCHEDULE_SUCCESS, lessonService.getLessonRegularSchedule(userIdx, lessonIdx));
    }

    @GetMapping("/{lessonIdx}/progress")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetLessonProgressResponse> getLessonProgress(@UserIdx final Long userIdx,
                                                                    @PathVariable final Long lessonIdx) {
        return ApiResponse.success(SuccessStatus.GET_LESSON_PROGRESS_SUCCESS, lessonService.getLessonProgress(userIdx, lessonIdx));
    }

    @GetMapping("/{lessonIdx}/detail")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetLessonDetailResponse> getLessonDetail(@UserIdx final Long userIdx,
                                                                @PathVariable final Long lessonIdx) {
        return ApiResponse.success(SuccessStatus.GET_LESSON_DETAIL_SUCCESS, lessonService.getLessonDetail(userIdx, lessonIdx));
    }


    @PatchMapping("/parents")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updateLessonParents(
            @UserIdx final Long userIdx,
            @Valid @RequestBody final UpdateLessonParentsRequest request) {

        lessonService.updateLessonParents(userIdx, request.getLessonCode());
        return ApiResponse.success(SuccessStatus.UPDATE_LESSON_PARENTS_SUCCESS);

    }


    @DeleteMapping("/{lessonIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteLesson(@UserIdx final Long userIdx,
                                    @PathVariable final Long lessonIdx) {
        lessonService.deleteLesson(userIdx, lessonIdx);

        return ApiResponse.success(SuccessStatus.DELETE_LESSON_SUCCESS);
    }


}