package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UpdateScheduleAttendanceResponseDto {
    private boolean isLastCount;
    private TodayResponseDto attendanceSchedule;

    public static UpdateScheduleAttendanceResponseDto of(boolean isLastCount, LocalDate now) {
        return UpdateScheduleAttendanceResponseDto.builder()
                .isLastCount(isLastCount)
                .attendanceSchedule(TodayResponseDto.of(DateAndTimeConvert.localDateConvertString(now), DateAndTimeConvert.localDateConvertDayOfWeek(now)))
                .build();
    }
}
