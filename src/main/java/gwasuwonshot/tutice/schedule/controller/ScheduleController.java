package gwasuwonshot.tutice.schedule.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.schedule.dto.response.getScheduleByLesson.GetScheduleByLessonResponse;
import gwasuwonshot.tutice.schedule.dto.request.GetTemporaryScheduleRequest;
import gwasuwonshot.tutice.schedule.dto.request.UpdateScheduleAttendanceRequest;
import gwasuwonshot.tutice.schedule.dto.request.UpdateScheduleRequest;
import gwasuwonshot.tutice.schedule.dto.response.getLatestScheduleByTeacher.GetLatestScheduleByTeacherResponse;
import gwasuwonshot.tutice.schedule.dto.response.getMissingAttendanceScheduleByTeacher.GetMissingAttendanceScheduleByTeacherResponse;
import gwasuwonshot.tutice.schedule.dto.response.getScheduleByUser.GetScheduleByUserResponse;
import gwasuwonshot.tutice.schedule.dto.response.getTemporarySchedule.GetTemporaryScheduleResponse;
import gwasuwonshot.tutice.schedule.dto.response.getTodayScheduleByParents.GetTodayScheduleByParentsResponse;
import gwasuwonshot.tutice.schedule.dto.response.getTodayScheduleByTeacher.GetTodayScheduleByTeacherResponse;
import gwasuwonshot.tutice.schedule.dto.response.updateScheduleAttendance.UpdateScheduleAttendanceResponse;
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
 public ApiResponse<GetTemporaryScheduleResponse> getTemporarySchedule(@RequestBody @Valid final GetTemporaryScheduleRequest request) {
  return ApiResponse.success(SuccessStatus.GET_TEMPORARY_SCHEDULE_SUCCESS,
          scheduleService.getTemporarySchedule(request) );
 }

 @Scheduled(cron="0 0/30 * * * ?", zone="GMT+9:00")
 public void postMissingAttendance() throws IOException {
  scheduleService.postImmediateMissingAttendance();
  scheduleService.postMissingAttendance();
 }

 @GetMapping("")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<GetScheduleByUserResponse> getScheduleByUser(@UserIdx final Long userIdx,
                                                                 @RequestParam final String month) {
  return ApiResponse.success(SuccessStatus.GET_SCHEDULE_BY_USER_SUCCESS,
          scheduleService.getScheduleByUser(userIdx, month) );
 }

 @GetMapping("/today/parents")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<GetTodayScheduleByParentsResponse> getTodayScheduleByParents(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_TODAY_SCHEDULE_BY_PARENTS_SUCCESS,
          scheduleService.getTodayScheduleByParents(userIdx) );
 }

 @GetMapping("/lesson/{lessonIdx}")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<List<GetScheduleByLessonResponse>> getScheduleByLesson(
         @UserIdx final Long userIdx,
         @PathVariable final Long lessonIdx) {

  return ApiResponse.success(SuccessStatus.GET_SCHEDULE_BY_LESSON_SUCCESS,
          scheduleService.getScheduleByLesson(userIdx, lessonIdx));
 }


 @GetMapping("/today/teacher")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<GetTodayScheduleByTeacherResponse> getTodayScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_TODAY_SCHEDULE_BY_TEACHER_SUCCESS,
          scheduleService.getTodayScheduleByTeacher(userIdx) );
 }

 @GetMapping("/missing-attendance")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<GetMissingAttendanceScheduleByTeacherResponse> getMissingAttendanceScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_MISSING_ATTENDANCE_SCHEDULE_BY_TEACHER_SUCCESS,
          scheduleService.getMissingAttendanceScheduleByTeacher(userIdx) );
 }



 @GetMapping("/latest")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<GetLatestScheduleByTeacherResponse> getLatestScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_LATEST_SCHEDULE_BY_TEACHER,
          scheduleService.getLatestScheduleByTeacher(userIdx) );
 }



 @GetMapping("/lesson/{lessonIdx}/missing-attendance/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<Boolean> getMissingAttendanceExistenceByLesson(@UserIdx final Long userIdx,
                                                                      @PathVariable final Long lessonIdx) {
  return ApiResponse.success(SuccessStatus.GET_MISSING_ATTENDANCE_EXISTENCE_BY_LESSON_EXIST_SUCCESS,
          scheduleService.getMissingAttendanceExistenceByLesson(userIdx, lessonIdx));
 }

 @GetMapping("/missing-attendance/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<Boolean> getMissingAttendanceExistenceByTeacher(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_MISSING_ATTENDANCE_EXISTENCE_BY_TEACHER_SUCCESS,
          scheduleService.getMissingAttendanceExistenceByTeacher(userIdx) );
 }


 @GetMapping("/today/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<Boolean> getTodayScheduleExistenceByTeacher(@UserIdx final Long userIdx) {
  return ApiResponse.success(SuccessStatus.GET_TODAY_SCHEDULE_EXISTENCE_BY_TEACHER_SUCCESS,
          scheduleService.getTodayScheduleExistenceByTeacher(userIdx) );
 }

 @GetMapping("/{scheduleIdx}/attendance/existence")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<Boolean> getAttendanceExistenceBySchedule(@UserIdx final Long userIdx,
                                                                 @PathVariable final Long scheduleIdx) {
  return ApiResponse.success(SuccessStatus.GET_ATTENDANCE_EXISTENCE_BY_SCHEDULE,
          scheduleService.getAttendanceExistenceBySchedule(userIdx, scheduleIdx));
 }


 @PatchMapping("")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse updateSchedule(@UserIdx final Long userIdx,
                                      @RequestBody @Valid final UpdateScheduleRequest request) {
  scheduleService.updateSchedule(userIdx, request);
  return ApiResponse.success(SuccessStatus.UPDATE_SCHEDULE_SUCCESS);
 }

 @PatchMapping("/attendance")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponse<UpdateScheduleAttendanceResponse> updateScheduleAttendance(@UserIdx final Long userIdx,
                                                                               @RequestBody @Valid final UpdateScheduleAttendanceRequest request) {
  return ApiResponse.success(SuccessStatus.UPDATE_SCHEDULE_ATTENDANCE_SUCCESS,
          scheduleService.updateScheduleAttendance(userIdx, request));
 }

}


