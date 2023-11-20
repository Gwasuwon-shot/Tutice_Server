package gwasuwonshot.tutice.lesson.dto.response.getLessonByParents;

import gwasuwonshot.tutice.lesson.dto.response.LessonResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonByParentsResponse {
    List<LessonResponse> lessonList;

    public static GetLessonByParentsResponse of(List<LessonResponse> lessonList) {
        return new GetLessonByParentsResponse(lessonList);

    }
}
