package gwasuwonshot.tutice.schedule.dto.response;

import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ScheduleByUser {
    private Long idx;
    private String studentName;
    private String subject;
    private String startTime;
    private String endTime;

    public static ScheduleByUser of(Long idx, String studentName, String subject, String startTime, String endTime) {
        return ScheduleByUser.builder()
                .idx(idx)
                .studentName(studentName)
                .subject(subject)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
