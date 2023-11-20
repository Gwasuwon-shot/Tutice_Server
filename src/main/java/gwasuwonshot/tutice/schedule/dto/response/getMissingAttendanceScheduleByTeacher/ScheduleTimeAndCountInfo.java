package gwasuwonshot.tutice.schedule.dto.response.getMissingAttendanceScheduleByTeacher;

import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ScheduleTimeAndCountInfo {
    private Long idx;
    private String startTime;
    private String endTime;
    private Integer expectedCount;

    public static ScheduleTimeAndCountInfo of(Long idx, String startTime, String endTime, Integer expectedCount) {
        return ScheduleTimeAndCountInfo.builder()
                .idx(idx)
                .startTime(startTime)
                .endTime(endTime)
                .expectedCount(expectedCount)
                .build();
    }
}
