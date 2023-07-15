package gwasuwonshot.tutice.schedule.controller;

import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
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

}

