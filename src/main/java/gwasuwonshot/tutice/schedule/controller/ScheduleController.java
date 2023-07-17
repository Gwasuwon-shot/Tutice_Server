package gwasuwonshot.tutice.schedule.controller;

import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.schedule.dto.request.UpdateScheduleRequestDto;
import gwasuwonshot.tutice.schedule.dto.response.GetMissingAttendanceScheduleResponseDto;
import gwasuwonshot.tutice.schedule.dto.response.GetScheduleByUserResponseDto;
import gwasuwonshot.tutice.schedule.dto.response.GetTodayScheduleByTeacherResponseDto;
import gwasuwonshot.tutice.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
                                      @RequestBody final UpdateScheduleRequestDto request) {
  scheduleService.updateSchedule(userIdx, request);
  return ApiResponseDto.success(SuccessStatus.UPDATE_SCHEDULE);
 }

}

