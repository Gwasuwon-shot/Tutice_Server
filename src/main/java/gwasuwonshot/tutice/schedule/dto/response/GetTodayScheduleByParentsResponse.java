package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.dto.GetTodayDateResponse;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetTodayScheduleByParentsResponse {
    private String parentsName;
    private GetTodayDateResponse today;
    private List<ScheduleResponse> scheduleList;

    public static GetTodayScheduleByParentsResponse of(String name) {
        return GetTodayScheduleByParentsResponse.builder()
                .parentsName(name)
                .build();
    }

    public static GetTodayScheduleByParentsResponse ofTodaySchedule(String name, LocalDate now, List<Schedule> scheduleList) {
        return GetTodayScheduleByParentsResponse.builder()
                .parentsName(name)
                .today(GetTodayDateResponse.of())
                .scheduleList(scheduleList.stream().map(s -> ScheduleResponse.of(s.getIdx(), s.getLesson().getStudentName(), s.getLesson().getTeacher().getName(), s.getLesson().getSubject(), s.getStartTime(), s.getEndTime())).collect(Collectors.toList()))
                .build();
    }
}