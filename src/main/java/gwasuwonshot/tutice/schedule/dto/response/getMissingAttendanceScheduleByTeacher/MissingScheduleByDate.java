package gwasuwonshot.tutice.schedule.dto.response.getMissingAttendanceScheduleByTeacher;

import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MissingScheduleByDate {
    private String date;
    private String dayOfWeek;
    private List<MissingAttendanceSchedule> missingAttedanceScheduleList;


    public static MissingScheduleByDate of(String date, String dayOfWeek, List<Schedule> scheduleList, List<Integer> scheduleCountList) {
        AtomicInteger index = new AtomicInteger();
        return MissingScheduleByDate.builder()
                .date(date)
                .dayOfWeek(dayOfWeek)
                .missingAttedanceScheduleList(scheduleList.stream()
                        .map(s -> MissingAttendanceSchedule.of(s, scheduleCountList.get(index.getAndIncrement())))
                        .collect(Collectors.toList()))
                .build();
    }
}
