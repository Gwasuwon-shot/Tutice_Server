package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TodaySchedule {
    private Boolean isMissingAttendanceByLesson;
    private TodayScheduleByTeacher lesson;
    private TodayScheduleDetailByTeacher schedule;
    private Integer timeStatus;

    public static TodaySchedule of(boolean isMissingAttendance, Schedule schedule, Integer timeStatus, Integer expectedCount) {
        return TodaySchedule.builder()
                .isMissingAttendanceByLesson(isMissingAttendance)
                .lesson(TodayScheduleByTeacher.of(schedule.getLesson().getIdx(), schedule.getLesson().getStudentName(), schedule.getLesson().getSubject()))
                .schedule(TodayScheduleDetailByTeacher.of(schedule.getIdx(), schedule.getStatus().getValue(), expectedCount))
                .timeStatus(timeStatus)
                .build();
    }
}
