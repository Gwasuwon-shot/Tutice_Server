package gwasuwonshot.tutice.schedule.dto.response.getTemporarySchedule;


import gwasuwonshot.tutice.schedule.dto.response.ScheduleResponse;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;


import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TemporarySchedule {
    private String date;
    private List<ScheduleResponse> scheduleList;


    public static TemporarySchedule of(String studentName, String subject, String date, List<Schedule> scheduleList) {
        return TemporarySchedule.builder()
                .date(date)
                .scheduleList(scheduleList.stream().map(s -> ScheduleResponse.of(studentName, subject, s.getStartTime(), s.getEndTime())).collect(Collectors.toList()))
                .build();
    }
}
