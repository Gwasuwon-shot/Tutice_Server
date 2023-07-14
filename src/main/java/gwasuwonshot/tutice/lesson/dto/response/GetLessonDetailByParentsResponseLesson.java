package gwasuwonshot.tutice.lesson.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonDetailByParentsResponseLesson {

    private Long idx;
    private String teacherName;
    private GetLessonDetailByParentsResponseLesson account;

    public static GetLessonDetailByParentsResponseLesson of(Long idx, String teacherName,GetLessonDetailByParentsResponseLesson account) {
        return new GetLessonDetailByParentsResponseLesson(idx,teacherName,account);
    }

}

