package gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LatestRegularSchedule {
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    public static LatestRegularSchedule of(String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return new LatestRegularSchedule(dayOfWeek,
                DateAndTimeConvert.localTimeConvertString(startTime),
                DateAndTimeConvert.localTimeConvertString(endTime));

    }
}
