package gwasuwonshot.tutice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestAttendanceNotificationResponseDto {
    private Long scheduleIdx;

    public static RequestAttendanceNotificationResponseDto of(Long scheduleIdx) {
        return new RequestAttendanceNotificationResponseDto(scheduleIdx);
    }

}
