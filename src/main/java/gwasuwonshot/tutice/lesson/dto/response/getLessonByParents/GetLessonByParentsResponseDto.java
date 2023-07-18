package gwasuwonshot.tutice.lesson.dto.response.getLessonByParents;

import gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenance.GetMissingMaintenanceLesson;
import gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenance.GetMissingMaintenanceLessonResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonByParentsResponseDto {
    List<GetLessonByParents> lessonList;

    public static GetLessonByParentsResponseDto of(List<GetLessonByParents> lessonList) {
        return new GetLessonByParentsResponseDto(lessonList);

    }
}
