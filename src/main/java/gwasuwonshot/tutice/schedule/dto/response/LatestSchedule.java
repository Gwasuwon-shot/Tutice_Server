package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class LatestSchedule {
    private TodayScheduleByTeacher lesson;
    private ScheduleByTime schedule;

    public static LatestSchedule of(Lesson lesson, Schedule schedule) {
        return LatestSchedule.builder()
                .lesson(TodayScheduleByTeacher.of(lesson.getIdx(), lesson.getStudentName(), lesson.getSubject()))
                .schedule(ScheduleByTime.of(schedule.getIdx(), schedule.getStartTime(), schedule.getEndTime()))
                .build();
    }

}
