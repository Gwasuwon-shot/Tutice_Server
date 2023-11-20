package gwasuwonshot.tutice.schedule.dto.response.getScheduleByUser;



import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ScheduleByDate {
    private String date;
    private String dayOfWeek;
    private List<DailySchedule> dailyScheduleList;

    public static ScheduleByDate of(String date, String dayOfWeek, List<Schedule> scheduleList) {
        return ScheduleByDate.builder()
                .date(date)
                .dayOfWeek(dayOfWeek)
                .dailyScheduleList(scheduleList.stream()
                        .map(s -> DailySchedule.of(s.getLesson().getIdx(), s))
                        .collect(Collectors.toList()))
                .build();
    }
}
