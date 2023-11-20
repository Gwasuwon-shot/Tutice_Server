package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UpdateScheduleAttendanceResponse {
    private boolean isLastCount;
    private TodayResponse attendanceSchedule;

    public static UpdateScheduleAttendanceResponse of(boolean isLastCount, LocalDate now) {
        return UpdateScheduleAttendanceResponse.builder()
                .isLastCount(isLastCount)
                .attendanceSchedule(TodayResponse.of(DateAndTimeConvert.localDateConvertString(now), DateAndTimeConvert.localDateConvertDayOfWeek(now)))
                .build();
    }
}
