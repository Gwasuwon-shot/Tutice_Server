package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetTodayScheduleByTeacherResponseDto {
    private String teacherName;
    private Boolean isTodaySchedule;
    private TodaySchedule todaySchedule;

    // case1
    public static GetTodayScheduleByTeacherResponseDto of(String name) {
        return GetTodayScheduleByTeacherResponseDto.builder()
                .teacherName(name)
                .isTodaySchedule(false)
                .build();
    }

    // case 2,3,4
    public static GetTodayScheduleByTeacherResponseDto ofTodaySchedule(String name, Schedule schedule, Integer timeStatus, Integer scheduleCount) {
        return GetTodayScheduleByTeacherResponseDto.builder()
                .teacherName(name)
                .isTodaySchedule(true)
                .todaySchedule(TodaySchedule.of(schedule, timeStatus, scheduleCount))
                .build();
    }

    // case 5
    public static GetTodayScheduleByTeacherResponseDto ofCompletedAttendance(String name) {
        return GetTodayScheduleByTeacherResponseDto.builder()
                .teacherName(name)
                .isTodaySchedule(true)
                .build();
    }
}
