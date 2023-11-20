package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetLatestScheduleByTeacherResponse {
    private DateAndDayOfWeek latestScheduleDay;
    private List<LatestScheduleByTeacher> latestScheduleByTeacherList;

    public static GetLatestScheduleByTeacherResponse of(LocalDate date, List<Schedule> latestScheduleList) {
        return GetLatestScheduleByTeacherResponse.builder()
                .latestScheduleDay(DateAndDayOfWeek.of(DateAndTimeConvert.localDateConvertString(date), DateAndTimeConvert.localDateConvertDayOfWeek(date)))
                .latestScheduleByTeacherList(latestScheduleList.stream().map(s -> LatestScheduleByTeacher.of(s.getLesson(), s)).collect(Collectors.toList()))
                .build();
    }
}
