package gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonByTeacher {
    private Long idx;
    private String studentName;
    private String subject;
    private Long percent;
    private Boolean isFinished;
    private LatestRegularSchedule latestRegularSchedule;

    public static LessonByTeacher of(Long idx, String studentName, String subject, Long percent, Boolean isFinished, LatestRegularSchedule latestRegularSchedule) {
        return new LessonByTeacher(idx, studentName, subject, percent, isFinished, latestRegularSchedule);

    }
}
