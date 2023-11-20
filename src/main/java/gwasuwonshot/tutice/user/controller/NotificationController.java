package gwasuwonshot.tutice.user.controller;

import gwasuwonshot.tutice.common.dto.ApiResponse;
import gwasuwonshot.tutice.common.exception.SuccessStatus;
import gwasuwonshot.tutice.common.resolver.userIdx.UserIdx;
import gwasuwonshot.tutice.user.dto.response.RequestAttendanceNotificationResponse;
import gwasuwonshot.tutice.user.dto.response.RequestPaymentRecordNotificationResponse;
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



    @GetMapping("/schedule/{scheduleIdx}/attendance")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<RequestAttendanceNotificationResponse> requestAttendanceNotification(
            @UserIdx final Long userIdx,
            @PathVariable final Long scheduleIdx) {

        return ApiResponse.success(SuccessStatus.REQUEST_ATTENDANCE_NOTIFICATION_SUCCESS,
                RequestAttendanceNotificationResponse.of(
                        notificationService.requestAttendanceNotification(userIdx,scheduleIdx)
                ));

    }

    @GetMapping("/lesson/{lessonIdx}/payment-record")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<RequestPaymentRecordNotificationResponse> requestPaymentRecordNotification(
            @UserIdx final Long userIdx,
            @PathVariable final Long lessonIdx) {

        return ApiResponse.success(SuccessStatus.REQUEST_PAYMENT_RECORD_NOTIFICATION_SUCCESS,
                RequestPaymentRecordNotificationResponse.of(
                        notificationService.requestPaymentRecordNotification(userIdx,lessonIdx)
                ));

    }
}
