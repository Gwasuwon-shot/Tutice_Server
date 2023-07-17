package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TodaySchedule {
    private TodayScheduleByTeacher lesson;
    private TodayScheduleDetailByTeacher schedule;
    private Integer timeStatus;

    public static TodaySchedule of(Schedule schedule, Integer timeStatus, Integer expectedCount) {
        return TodaySchedule.builder()
                .lesson(TodayScheduleByTeacher.of(schedule.getLesson().getIdx(), schedule.getLesson().getStudentName(), schedule.getLesson().getSubject()))
                .schedule(TodayScheduleDetailByTeacher.of(schedule.getIdx(), schedule.getStatus().getValue(), expectedCount))
                .timeStatus(timeStatus)
                .build();
    }
}
