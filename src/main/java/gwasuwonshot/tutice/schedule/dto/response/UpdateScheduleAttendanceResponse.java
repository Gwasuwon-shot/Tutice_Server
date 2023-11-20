package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.dto.GetTodayDateResponse;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UpdateScheduleAttendanceResponse {
    private boolean isLastCount;
    private GetTodayDateResponse attendanceSchedule;

    public static UpdateScheduleAttendanceResponse of(boolean isLastCount, LocalDate now) {
        return UpdateScheduleAttendanceResponse.builder()
                .isLastCount(isLastCount)
                .attendanceSchedule(GetTodayDateResponse.of())
                .build();
    }
}
