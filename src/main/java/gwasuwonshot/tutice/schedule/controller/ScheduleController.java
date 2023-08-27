package gwasuwonshot.tutice.schedule.controller;

import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
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


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

 private final ScheduleService scheduleService;

 @GetMapping("/today/parents")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<?> getTodayScheduleByParents(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_TODAY_SCHEDULE_BY_PARENTS_SUCCESS, scheduleService.getTodayScheduleByParents(userIdx) );
 }

 @GetMapping("")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<GetScheduleByUserResponseDto> getScheduleByUser(@UserIdx final Long userIdx,
                                                                       @RequestParam final String month) {
  return ApiResponseDto.success(SuccessStatus.GET_SCHEDULE_BY_USER_SUCCESS, scheduleService.getScheduleByUser(userIdx, month) );
 }

 @GetMapping("/today/teacher")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<GetTodayScheduleByTeacherResponseDto> getTodayScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_TODAY_SCHEDULE_BY_TEACHER_SUCCESS, scheduleService.getTodayScheduleByTeacher(userIdx) );
 }

 @GetMapping("/attendance/missing")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<GetMissingAttendanceScheduleResponseDto> getMissingAttendanceSchedule(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_MISSING_ATTENDANCE_SCHEDULE_SUCCESS, scheduleService.getMissingAttendanceSchedule(userIdx) );
 }

 @PatchMapping("")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto updateSchedule(@UserIdx final Long userIdx,
                                      @RequestBody @Valid final UpdateScheduleRequestDto request) {
  scheduleService.updateSchedule(userIdx, request);
  return ApiResponseDto.success(SuccessStatus.UPDATE_SCHEDULE);
 }

 @GetMapping("/latest")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<GetLatestScheduleByTeacherResponseDto> getLatestScheduleByTeacher(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_LATEST_SCHEDULE_BY_TEACHER, scheduleService.getLatestScheduleByTeacher(userIdx) );
 }

 @PatchMapping("/attendance")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<UpdateScheduleAttendanceResponseDto> updateScheduleAttendance(@UserIdx final Long userIdx,
                                                                                     @RequestBody @Valid final UpdateScheduleAttendanceRequestDto request) {
  return ApiResponseDto.success(SuccessStatus.UPDATE_SCHEDULE_ATTENDANCE_SUCCESS, scheduleService.updateScheduleAttendance(userIdx, request));
 }

 @Scheduled(cron="0 0/30 * * * ?", zone="GMT+9:00")
 public void postMissingAttendance() throws IOException {
  scheduleService.postImmediateMissingAttendance();
  scheduleService.postMissingAttendance();
 }

 @PostMapping("/temporary")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<GetTemporaryScheduleResponseDto> getTemporarySchedule(@RequestBody @Valid final GetTemporaryScheduleRequestDto request) {
  return ApiResponseDto.success(SuccessStatus.GET_TEMPORARY_SCHEDULE_SUCCESS, scheduleService.getTemporarySchedule(request) );
 }

 @GetMapping("/attendance/missing/exists")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<Boolean> getMissingAttendanceExist(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_MISSING_ATTENDANCE_EXIST_SUCCESS, scheduleService.getMissingAttendanceExist(userIdx) );
 }

 @GetMapping("/maintenance/missing/exists")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<Boolean> getMissingMaintenanceExist(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_MISSING_MAINTENANCE_EXIST_SUCCESS, scheduleService.getMissingMaintenanceExist(userIdx) );
 }

 @GetMapping("/today/exists")
 @ResponseStatus(HttpStatus.OK)
 public ApiResponseDto<Boolean> getTodayScheduleExist(@UserIdx final Long userIdx) {
  return ApiResponseDto.success(SuccessStatus.GET_TODAY_SCHEDULE_EXIST_SUCCESS, scheduleService.getTodayScheduleExist(userIdx) );
 }

}

