package gwasuwonshot.tutice.user.controller;

import gwasuwonshot.tutice.common.dto.ApiResponseDto;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.lesson.dto.response.getLessonSchedule.GetLessonScheduleByUserResponseDto;
import gwasuwonshot.tutice.user.dto.response.RequestAttendanceNotificationResponseDto;
import gwasuwonshot.tutice.user.entity.Role;
import gwasuwonshot.tutice.user.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@Tag(name = "Notification", description = "푸쉬 알림 API Document")
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping("/attendance/{scheduleIdx}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<RequestAttendanceNotificationResponseDto> requestAttendanceNotification(
            @UserIdx final Long userIdx,
            @PathVariable final Long scheduleIdx) {

        return ApiResponseDto.success(SuccessStatus.REQUEST_ATTENDANCE_NOTIFICATION_SUCCESS,
                RequestAttendanceNotificationResponseDto.of(
                        notificationService.requestAttendanceNotification(userIdx,scheduleIdx)
                ));


    }
}
