package gwasuwonshot.tutice.schedule.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.lesson.dto.response.getLessonSchedule.GetLessonScheduleResponse;
import gwasuwonshot.tutice.schedule.dto.request.GetTemporaryScheduleRequest;
import gwasuwonshot.tutice.schedule.dto.request.UpdateScheduleAttendanceRequest;
import gwasuwonshot.tutice.schedule.dto.request.UpdateScheduleRequest;
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

 @GetMapping("/today/parents")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<?> getTodayScheduleByParents(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_TODAY_SCHEDULE_BY_PARENTS_SUCCESS, scheduleService.getTodayScheduleByParents(userIdx) );
 }

 @GetMapping("/lesson/{lessonIdx}")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<List<GetLessonScheduleResponse>> getLessonSchedule(
         @UserIdx final Long userIdx,
         @PathVariable final Long lessonIdx) {

  return ApiResponse.success(SuccessStatus.GET_LESSON_SCHEDULE_SUCCESS,
          scheduleService.getLessonSchedule(userIdx, lessonIdx));
 }
 @GetMapping("")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<GetScheduleByUserResponse> getScheduleByUser(@UserIdx final Long userIdx,
                                                                 @RequestParam final String month) {
  return ApiResponse.success(SuccessStatus.GET_SCHEDULE_BY_USER_SUCCESS, scheduleService.getScheduleByUser(userIdx, month) );
 }

 @GetMapping("/today/teacher")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<GetTodayScheduleByTeacherResponse> getTodayScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_TODAY_SCHEDULE_BY_TEACHER_SUCCESS, scheduleService.getTodayScheduleByTeacher(userIdx) );
 }

 @GetMapping("/missing-attendance")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<GetMissingAttendanceScheduleResponse> getMissingAttendanceScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_MISSING_ATTENDANCE_SCHEDULE_SUCCESS, scheduleService.getMissingAttendanceScheduleByTeacher(userIdx) );
 }

 @PatchMapping("")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse updateSchedule(@UserIdx final Long userIdx,
                                   @RequestBody @Valid final UpdateScheduleRequest request) {
  scheduleService.updateSchedule(userIdx, request);
  return ApiResponse.success(SuccessStatus.UPDATE_SCHEDULE);
 }

 @GetMapping("/latest")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<GetLatestScheduleByTeacherResponse> getLatestScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_LATEST_SCHEDULE_BY_TEACHER, scheduleService.getLatestScheduleByTeacher(userIdx) );
 }

 @PatchMapping("/attendance")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<UpdateScheduleAttendanceResponse> updateScheduleAttendance(@UserIdx final Long userIdx,
                                                                               @RequestBody @Valid final UpdateScheduleAttendanceRequest request) {
  return ApiResponse.success(SuccessStatus.UPDATE_SCHEDULE_ATTENDANCE_SUCCESS, scheduleService.updateScheduleAttendance(userIdx, request));
 }

 @Scheduled(cron="0 0/30 * * * ?", zone="GMT+9:00")
 public void postMissingAttendance() throws IOException {
  scheduleService.postImmediateMissingAttendance();
  scheduleService.postMissingAttendance();
 }

 @PostMapping("/temporary")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<GetTemporaryScheduleResponse> getTemporarySchedule(@RequestBody @Valid final GetTemporaryScheduleRequest request) {
  return ApiResponse.success(SuccessStatus.GET_TEMPORARY_SCHEDULE_SUCCESS, scheduleService.getTemporarySchedule(request) );
 }


 @GetMapping("/lesson/{lessonIdx}/missing-attendance/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<Boolean> getMissingAttendanceExistenceByLesson(@UserIdx final Long userIdx,
                                                                   @PathVariable final Long lessonIdx) {
  return ApiResponse.success(SuccessStatus.GET_MISSING_MAINTENANCE_BY_LESSON_EXIST_SUCCESS, scheduleService.getMissingAttendanceExistenceByLesson(userIdx, lessonIdx));
 }

 @GetMapping("/missing-attendance/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<Boolean> getMissingAttendanceExistenceByTeacher(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_MISSING_ATTENDANCE_EXIST_SUCCESS, scheduleService.getMissingAttendanceExistenceByTeacher(userIdx) );
 }



 @GetMapping("/today/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<Boolean> getTodayScheduleExistenceByTeacher(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_TODAY_SCHEDULE_EXIST_SUCCESS, scheduleService.getTodayScheduleExistenceByTeacher(userIdx) );
 }

 @GetMapping("{scheduleIdx}/attendance/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<Boolean> getAttendanceExistenceByTeacher(@UserIdx final Long userIdx,
                                                             @PathVariable final Long scheduleIdx) {
  return ApiResponse.success(SuccessStatus.GET_TODAY_SCHEDULE_EXIST_SUCCESS, scheduleService.getAttendanceExistenceByTeacher(userIdx, scheduleIdx));
 }

}

