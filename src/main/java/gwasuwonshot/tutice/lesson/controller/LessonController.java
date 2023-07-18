package gwasuwonshot.tutice.lesson.controller;

import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonMaintenanceRequestDto;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequestDto;
import gwasuwonshot.tutice.lesson.dto.request.UpdateLessonParentsRequestDto;
import gwasuwonshot.tutice.lesson.dto.response.CreateLessonResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.GetLessonByUserResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByParents.GetLessonByParentsResponseDto;
import gwasuwonshot.tutice.lesson.dto.response.getLessonDetail.GetLessonDetailByParentsResponseDto;
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

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonByUserResponseDto> getLessonByUser(@UserIdx final Long userIdx) {

        return ApiResponseDto.success(SuccessStatus.GET_LESSON_BY_USER_SUCCESS,
                lessonService.getLessonByUser(userIdx));


    }

    @GetMapping("/detail/parents/{lessonIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetLessonDetailByParentsResponseDto> getLessonDetailByParents(
            @UserIdx final Long userIdx
            , @PathVariable final Long lessonIdx) {


        return ApiResponseDto.success(SuccessStatus.GET_LESSON_DETAIL_BY_PARENTS_SUCCESS,
                lessonService.getLessonDetailByParents(userIdx, lessonIdx));


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




    @GetMapping("/maintenance/missing")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<GetMissingMaintenanceLessonResponseDto> getMissingMaintenanceLesson(@UserIdx final Long userIdx) {


        return ApiResponseDto.success(SuccessStatus.GET_MISSING_MAINTENANCE_LESSON_SUCCESS,
                GetMissingMaintenanceLessonResponseDto.of(lessonService.getMissingMaintenanceLesson(userIdx))
        );


    }



}

