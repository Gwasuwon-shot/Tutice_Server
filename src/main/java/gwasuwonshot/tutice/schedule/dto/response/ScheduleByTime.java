package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.*;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ScheduleByTime {
    private Long idx;
    private String startTime;
    private String endTime;

    public static ScheduleByTime of(Long idx, LocalTime startTime, LocalTime endTime) {
        return ScheduleByTime.builder()
                .idx(idx)
                .startTime(DateAndTimeConvert.localTimeConvertString(startTime))
                .endTime(DateAndTimeConvert.localTimeConvertString(endTime))
                .build();
    }
}
