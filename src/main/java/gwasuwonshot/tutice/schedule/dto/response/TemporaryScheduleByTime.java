package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TemporaryScheduleByTime {
    private String studentName;
    private String subject;
    private String startTime;
    private String endTime;

    public static TemporaryScheduleByTime of(String studentName, String subject, LocalTime startTime, LocalTime endTime) {
        return TemporaryScheduleByTime.builder()
                .studentName(studentName)
                .subject(subject)
                .startTime(DateAndTimeConvert.localTimeConvertString(startTime))
                .endTime(DateAndTimeConvert.localTimeConvertString(endTime))
                .build();
    }
}
