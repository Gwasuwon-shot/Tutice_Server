package gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonByTeacher {
    private Long idx;
    private String studentName;
    private String subject;
    private Long percent;
    private Boolean isFinished;
    private GetLessonByTeacherLatestRegularSchedule latestRegularSchedule;

    public static GetLessonByTeacher of(Long idx, String studentName,String subject,Long percent, Boolean isFinished, GetLessonByTeacherLatestRegularSchedule latestRegularSchedule) {
        return new GetLessonByTeacher(idx, studentName, subject, percent, isFinished, latestRegularSchedule);

    }
}
