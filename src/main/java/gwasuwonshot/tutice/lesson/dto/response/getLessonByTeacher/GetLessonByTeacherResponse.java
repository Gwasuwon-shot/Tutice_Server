package gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonByTeacherResponse {
    private List<LessonByTeacher> lessonList;

    public static GetLessonByTeacherResponse of(List<LessonByTeacher> lessonList) {
        return new GetLessonByTeacherResponse(lessonList);

    }
}
