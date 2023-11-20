package gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonByTeacherLatestRegularSchedule { //TODO : request, response dto naming 어찌해야하지....
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    public static GetLessonByTeacherLatestRegularSchedule of(String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return new GetLessonByTeacherLatestRegularSchedule(dayOfWeek,
                DateAndTimeConvert.localTimeConvertString(startTime),
                DateAndTimeConvert.localTimeConvertString(endTime));

    }
}
