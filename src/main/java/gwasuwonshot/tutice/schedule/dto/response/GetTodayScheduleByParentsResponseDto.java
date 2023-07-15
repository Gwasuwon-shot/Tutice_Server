package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetTodayScheduleByParentsResponseDto {
    private String parentsName;
    private TodayResponseDto today;
    private List<ScheduleResponseDto> scheduleList;

    public static GetTodayScheduleByParentsResponseDto of(String name) {
        return GetTodayScheduleByParentsResponseDto.builder()
                .parentsName(name)
                .build();
    }

    public static GetTodayScheduleByParentsResponseDto ofTodaySchedule(String name, Date now, List<Schedule> scheduleList) {
        return GetTodayScheduleByParentsResponseDto.builder()
                .parentsName(name)
                .today(TodayResponseDto.of(DateAndTimeConvert.dateConvertString(now), DateAndTimeConvert.dateConvertDayOfWeek(now)))
                .scheduleList(scheduleList.stream().map(s -> ScheduleResponseDto.of(s.getIdx(), s.getLesson().getStudentName(), s.getLesson().getParents().getName(), s.getLesson().getSubject(), s.getStartTime(), s.getEndTime())).collect(Collectors.toList()))
                .build();
    }
}