package gwasuwonshot.tutice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestAttendanceNotificationResponse {
    private Long scheduleIdx;

    public static RequestAttendanceNotificationResponse of(Long scheduleIdx) {
        return new RequestAttendanceNotificationResponse(scheduleIdx);
    }

}
