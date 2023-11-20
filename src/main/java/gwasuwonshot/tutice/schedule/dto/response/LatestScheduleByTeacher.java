package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class LatestScheduleByTeacher {
    private ScheduleResponse lesson;
    private ScheduleResponse schedule;

    public static LatestScheduleByTeacher of(Lesson lesson, Schedule schedule) {
        return LatestScheduleByTeacher.builder()
                .lesson(ScheduleResponse.of(lesson.getIdx(), lesson.getStudentName(), lesson.getSubject()))
                .schedule(ScheduleResponse.of(schedule.getIdx(), schedule.getStartTime(), schedule.getEndTime()))
                .build();
    }

}
