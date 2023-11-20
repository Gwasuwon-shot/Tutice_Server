package gwasuwonshot.tutice.lesson.dto.response.getLessonByParents;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonByParentsResponse {
    List<GetLessonByParents> lessonList;

    public static GetLessonByParentsResponse of(List<GetLessonByParents> lessonList) {
        return new GetLessonByParentsResponse(lessonList);

    }
}
