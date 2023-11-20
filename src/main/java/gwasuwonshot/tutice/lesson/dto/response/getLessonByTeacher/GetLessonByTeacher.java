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
    private List<String> dayOfWeekList;

    public static GetLessonByTeacher of(Long idx, String studentName,String subject,Long percent, List<String> dayOfWeekList) {
        return new GetLessonByTeacher(idx, studentName, subject, percent, dayOfWeekList);

    }
}
