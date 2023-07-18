package gwasuwonshot.tutice.schedule.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TodayScheduleDetailByTeacher {
    private Long idx;
    private String status;
    private Integer expectedCount;

    public static TodayScheduleDetailByTeacher of(Long idx, String status, Integer expectedCount) {
        return TodayScheduleDetailByTeacher.builder()
                .idx(idx)
                .status(status)
                .expectedCount(expectedCount)
                .build();
    }
}
