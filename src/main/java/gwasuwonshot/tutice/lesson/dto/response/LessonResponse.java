package gwasuwonshot.tutice.lesson.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class LessonResponse {
    private Long idx;
    private String teacherName;
    private String studentName;
    private String subject;
    private Long count;
    private Long nowCount;
    private Long percent;

    public static LessonResponse of(Long idx, String teacherName, String studentName,
                                    String subject, Long count, Long nowCount , Long percent) {
        return LessonResponse.builder()
                .idx(idx)
                .teacherName(teacherName)
                .studentName(studentName)
                .subject(subject)
                .count(count)
                .nowCount(nowCount)
                .percent(percent)
                .build();
        }

    public static LessonResponse of(Long idx, String studentName, String subject, Long count) {
        return LessonResponse.builder()
                .idx(idx)
                .studentName(studentName)
                .subject(subject)
                .count(count)
                .build();
    }
}
