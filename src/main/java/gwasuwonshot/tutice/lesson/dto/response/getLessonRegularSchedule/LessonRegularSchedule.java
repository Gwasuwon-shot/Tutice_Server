package gwasuwonshot.tutice.lesson.dto.response.getLessonRegularSchedule;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonRegularSchedule {

    private List<String> dayOfWeekList;
    private String startTime;
    private String endTime;

    public static LessonRegularSchedule of(List<DayOfWeek> dayOfWeekList, LocalTime startTime, LocalTime endTime) {

        return new LessonRegularSchedule(
                dayOfWeekList.stream().map(DayOfWeek::getValue).collect(Collectors.toList()),
                DateAndTimeConvert.localTimeConvertString(startTime),
                DateAndTimeConvert.localTimeConvertString(endTime));
    }


}
