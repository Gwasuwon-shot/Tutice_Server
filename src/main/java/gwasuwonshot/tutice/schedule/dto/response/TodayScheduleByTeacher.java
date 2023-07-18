package gwasuwonshot.tutice.schedule.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TodayScheduleByTeacher {
    private Long idx;
    private String studentName;
    private String subject;

    public static TodayScheduleByTeacher of(Long idx, String studentName, String subject) {
        return TodayScheduleByTeacher.builder()
                .idx(idx)
                .studentName(studentName)
                .subject(subject)
                .build();
    }
}
