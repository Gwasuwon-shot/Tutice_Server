package gwasuwonshot.tutice.schedule.controller;

import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.lesson.dto.response.getLessonSchedule.GetLessonScheduleResponseDto;
import gwasuwonshot.tutice.schedule.dto.request.GetTemporaryScheduleRequestDto;
import gwasuwonshot.tutice.schedule.dto.request.UpdateScheduleAttendanceRequestDto;
import gwasuwonshot.tutice.schedule.dto.request.UpdateScheduleRequestDto;
import gwasuwonshot.tutice.schedule.dto.response.*;
import gwasuwonshot.tutice.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

 private final ScheduleService scheduleService;

 @PostMapping("/temporary")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<GetTemporaryScheduleResponseDto> getTemporarySchedule(@RequestBody @Valid final GetTemporaryScheduleRequestDto request) {
  return ApiResponseDto.success(SuccessStatus.GET_TEMPORARY_SCHEDULE_SUCCESS,
          scheduleService.getTemporarySchedule(request) );
 }

 @Scheduled(cron="0 0/30 * * * ?", zone="GMT+9:00")
 public void postMissingAttendance() throws IOException {
  scheduleService.postImmediateMissingAttendance();
  scheduleService.postMissingAttendance();
 }

 @GetMapping("")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<GetScheduleByUserResponseDto> getScheduleByUser(@UserIdx final Long userIdx,
                                                                       @RequestParam final String month) {
  return ApiResponseDto.success(SuccessStatus.GET_SCHEDULE_BY_USER_SUCCESS,
          scheduleService.getScheduleByUser(userIdx, month) );
 }

 @GetMapping("/today/parents")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<?> getTodayScheduleByParents(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_TODAY_SCHEDULE_BY_PARENTS_SUCCESS,
          scheduleService.getTodayScheduleByParents(userIdx) );
 }

 @GetMapping("/lesson/{lessonIdx}")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<List<GetLessonScheduleResponseDto>> getScheduleByLesson(
         @UserIdx final Long userIdx,
         @PathVariable final Long lessonIdx) {

  return ApiResponseDto.success(SuccessStatus.GET_SCHEDULE_BY_LESSON_SUCCESS,
          scheduleService.getScheduleByLesson(userIdx, lessonIdx));
 }


 @GetMapping("/today/teacher")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<GetTodayScheduleByTeacherResponseDto> getTodayScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_TODAY_SCHEDULE_BY_TEACHER_SUCCESS,
          scheduleService.getTodayScheduleByTeacher(userIdx) );
 }

 @GetMapping("/missing-attendance")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<GetMissingAttendanceScheduleResponseDto> getMissingAttendanceScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_MISSING_ATTENDANCE_SCHEDULE_BY_TEACHER_SUCCESS,
          scheduleService.getMissingAttendanceScheduleByTeacher(userIdx) );
 }



 @GetMapping("/latest")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<GetLatestScheduleByTeacherResponseDto> getLatestScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_LATEST_SCHEDULE_BY_TEACHER,
          scheduleService.getLatestScheduleByTeacher(userIdx) );
 }



 @GetMapping("/lesson/{lessonIdx}/missing-attendance/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<Boolean> getMissingAttendanceExistenceByLesson(@UserIdx final Long userIdx,
                                                           @PathVariable final Long lessonIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_MISSING_ATTENDANCE_EXISTENCE_BY_LESSON_EXIST_SUCCESS,
          scheduleService.getMissingAttendanceExistenceByLesson(userIdx, lessonIdx));
 }

 @GetMapping("/missing-attendance/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<Boolean> getMissingAttendanceExistenceByTeacher(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_MISSING_ATTENDANCE_EXISTENCE_BY_TEACHER_SUCCESS,
          scheduleService.getMissingAttendanceExistenceByTeacher(userIdx) );
 }


 @GetMapping("/today/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<Boolean> getTodayScheduleExistenceByTeacher(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_TODAY_SCHEDULE_EXISTENCE_BY_TEACHER_SUCCESS,
          scheduleService.getTodayScheduleExistenceByTeacher(userIdx) );
 }

 @GetMapping("/{scheduleIdx}/attendance/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<Boolean> getAttendanceExistenceBySchedule(@UserIdx final Long userIdx,
                                                                @PathVariable final Long scheduleIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_ATTENDANCE_EXISTENCE_BY_SCHEDULE,
          scheduleService.getAttendanceExistenceBySchedule(userIdx, scheduleIdx));
 }


 @PatchMapping("")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto updateSchedule(@UserIdx final Long userIdx,
                                      @RequestBody @Valid final UpdateScheduleRequestDto request) {
  scheduleService.updateSchedule(userIdx, request);
  return ApiResponseDto.success(SuccessStatus.UPDATE_SCHEDULE_SUCCESS);
 }

 @PatchMapping("/attendance")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<UpdateScheduleAttendanceResponseDto> updateScheduleAttendance(@UserIdx final Long userIdx,
                                                                                     @RequestBody @Valid final UpdateScheduleAttendanceRequestDto request) {
  return ApiResponseDto.success(SuccessStatus.UPDATE_SCHEDULE_ATTENDANCE_SUCCESS,
          scheduleService.updateScheduleAttendance(userIdx, request));
 }

}

