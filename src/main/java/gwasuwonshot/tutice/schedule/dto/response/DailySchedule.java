package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DailySchedule {

    private Long lessonIdx;
    private ScheduleResponse schedule;

    public static DailySchedule of(Long lessonIdx, Schedule schedule) {
        return DailySchedule.builder()
                .lessonIdx(lessonIdx)
                .schedule(ScheduleResponse.of(schedule.getIdx(),
                        schedule.getLesson().getStudentName(),
                        schedule.getLesson().getSubject(),
                        DateAndTimeConvert.localTimeConvertString(schedule.getStartTime()),
                        DateAndTimeConvert.localTimeConvertString(schedule.getEndTime())))
                .build();
    }
}
