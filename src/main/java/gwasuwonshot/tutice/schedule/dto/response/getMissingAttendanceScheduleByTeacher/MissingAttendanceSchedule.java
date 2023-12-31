package gwasuwonshot.tutice.schedule.dto.response.getMissingAttendanceScheduleByTeacher;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.schedule.dto.response.ScheduleResponse;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MissingAttendanceSchedule {
    private ScheduleResponse lesson;
    private ScheduleTimeAndCountInfo schedule;

    public static MissingAttendanceSchedule of(Schedule schedule, Integer scheduleCount) {
        return MissingAttendanceSchedule.builder()
                .lesson(ScheduleResponse.of(schedule.getLesson().getIdx(), schedule.getLesson().getStudentName(), schedule.getLesson().getSubject()))
                .schedule(ScheduleTimeAndCountInfo.of(schedule.getIdx(), DateAndTimeConvert.localTimeConvertString(schedule.getStartTime()), DateAndTimeConvert.localTimeConvertString(schedule.getEndTime()), scheduleCount))
                .build();
    }
}
