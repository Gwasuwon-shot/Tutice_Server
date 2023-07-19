package gwasuwonshot.tutice.lesson.dto.response.getLessonByTeacher;

import gwasuwonshot.tutice.lesson.dto.response.getLessonByParents.GetLessonByParents;
import gwasuwonshot.tutice.lesson.dto.response.getLessonByParents.GetLessonByParentsResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonByTeacherResponseDto {
    private List<GetLessonByTeacher> lessonList;

    public static GetLessonByTeacherResponseDto of(List<GetLessonByTeacher> lessonList) {
        return new GetLessonByTeacherResponseDto(lessonList);

    }
}
