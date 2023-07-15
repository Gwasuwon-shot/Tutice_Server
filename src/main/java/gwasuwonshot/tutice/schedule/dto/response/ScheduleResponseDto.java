package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.*;

import java.sql.Time;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ScheduleResponseDto {
    private Long idx;
    private String studentName;
    private String teacherName;
    private String subject;
    private String startTime;
    private String endTime;

    public static ScheduleResponseDto of(Long idx, String studentName, String teacherName, String subject, Time startTime, Time endTime) {
        return ScheduleResponseDto.builder()
                .idx(idx)
                .studentName(studentName)
                .teacherName(teacherName)
                .subject(subject)
                .startTime(DateAndTimeConvert.timeConvertString(startTime))
                .endTime(DateAndTimeConvert.timeConvertString(endTime))
                .build();
    }
}
