package gwasuwonshot.tutice.lesson.controller;

import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonMaintenanceRequestDto;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequestDto;
import gwasuwonshot.tutice.lesson.dto.request.UpdateLessonParentsRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.CreateLessonResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonAccountResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonByUserResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonDetailResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonProgressResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByParents.GetLessonByParentsResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher.GetLessonByTeacherResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getLessonSchedule.GetLessonScheduleResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenance.GetMissingMaintenanceLessonResponseDto;
import gwasuwonshot.tutice.lesson.service.LessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/existence")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonByUserResponseDto> getLessonExistenceByUser(@UserIdx final Long userIdx) {

        // TODO! DTO에도 existence 붙이기
        return ApiResponseDto.success(SuccessStatus.GET_LESSON_EXISTENCE_BY_USER_SUCCESS,
                lessonService.getLessonExistenceByUser(userIdx));


    }

    //TODO ! 일단 url만 우선 변경
//    @GetMapping("/schedule/{lessonIdx}")
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponseDto<List<GetLessonScheduleResponseDto>> getLessonSchedule(
//            @UserIdx final Long userIdx,
//            @PathVariable final Long lessonIdx) {
//
//        return ApiResponseDto.success(SuccessStatus.GET_LESSON_SCHEDULE_SUCCESS,
//                lessonService.getLessonSchedule(userIdx, lessonIdx));
//    }


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

    @PatchMapping("/parents")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateLessonParents(
            @UserIdx final Long userIdx,
            @Valid @RequestBody final UpdateLessonParentsRequestDto request) {

        lessonService.updateLessonParents(userIdx, request.getLessonCode());
        return ApiResponseDto.success(SuccessStatus.UPDATE_LESSON_PARENTS_SUCCESS);

    }

    @PostMapping("/maintenance")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto createLessonMaintenance(
            @UserIdx final Long userIdx,
            @Valid @RequestBody final CreateLessonMaintenanceRequestDto request) {

//        //연장시
//        //연장안할때
//        유저가 선생이 맞는지 확인
//        유효한 레슨인지, 선생의 레슨이 맞는지확인
//        연장안하면 -> isFinished 만 true로 변경
//                연장하면
//        가짜 paymentRecord생성
//        startDate는 해당 수업의 마지막 스케쥴날짜 +1
        if(lessonService.createLessonMaintenance(userIdx,request.getLessonIdx(),request.getIsLessonMaintenance())){
            return ApiResponseDto.success(SuccessStatus.AUTO_CREATE_SCHEDULE_FROM_LESSON_MAINTENANCE_SUCCESS);

        }
        else{
            return ApiResponseDto.success(SuccessStatus.FINISH_LESSON_SUCCESS);

        }


    }




    @GetMapping("/missing-maintenance")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetMissingMaintenanceLessonResponseDto> getMissingMaintenanceLessonByUser(@UserIdx final Long userIdx) {


        return ApiResponseDto.success(SuccessStatus.GET_MISSING_MAINTENANCE_LESSON_SUCCESS,
                GetMissingMaintenanceLessonResponseDto.of(lessonService.getMissingMaintenanceLessonByUser(userIdx))
        );


    }

    // TODO : url만 변경
//    @GetMapping("/account/{lessonIdx}")
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponseDto<GetLessonAccountResponseDto> getLessonAccount(@UserIdx final Long userIdx,
//                                                                        @PathVariable final Long lessonIdx) {
//        return ApiResponseDto.success(SuccessStatus.GET_LESSON_ACCOUNT_SUCCESS, lessonService.getLessonAccount(userIdx, lessonIdx));
//    }

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

    @GetMapping("/missing-maintenance/existence")
    // TODO SuccessMessage등 자잘한 변경사항 필요
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<Boolean> getMissingMaintenanceExistenceByTeacher(@UserIdx final Long userIdx) {
        return ApiResponseDto.success(SuccessStatus.GET_MISSING_MAINTENANCE_EXIST_SUCCESS, lessonService.getMissingMaintenanceExistenceByTeacher(userIdx) );
    }

    @DeleteMapping("/{lessonIdx}")
    // TODO SuccessMessage등 자잘한 변경사항 필요
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteLesson(@UserIdx final Long userIdx,
                                       @PathVariable final Long lessonIdx) {
        lessonService.deleteLesson(userIdx, lessonIdx);
        return ApiResponseDto.success(SuccessStatus.DELETE_LESSON_SUCCESS);
    }


}

