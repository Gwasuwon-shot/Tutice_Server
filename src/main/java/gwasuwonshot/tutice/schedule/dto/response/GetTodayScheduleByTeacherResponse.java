package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.schedule.dto.ScheduleResponse;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetTodayScheduleByTeacherResponse {
    private ScheduleResponse lesson;
    private TodayScheduleDetailByTeacher schedule;
    private Integer timeStatus;

    public static GetTodayScheduleByTeacherResponse of(Schedule schedule, Integer timeStatus, Integer expectedCount) {
        return GetTodayScheduleByTeacherResponse.builder()
                .lesson(ScheduleResponse.of(schedule.getLesson().getIdx(), schedule.getLesson().getStudentName(), schedule.getLesson().getSubject()))
                .schedule(TodayScheduleDetailByTeacher.of(schedule.getIdx(), schedule.getStatus().getValue(), expectedCount))
                .timeStatus(timeStatus)
                .build();
    }
}
