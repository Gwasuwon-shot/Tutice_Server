package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ScheduleByDateAndTime {
    private String date;
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    public static ScheduleByDateAndTime of(LocalDate date, LocalTime startTime, LocalTime endTime) {
        return ScheduleByDateAndTime.builder()
                .date(DateAndTimeConvert.localDateConvertString(date))
                .dayOfWeek(DateAndTimeConvert.localDateConvertDayOfWeek(date))
                .startTime(DateAndTimeConvert.localTimeConvertString(startTime))
                .endTime(DateAndTimeConvert.localTimeConvertString(endTime))
                .build();
    }
}
