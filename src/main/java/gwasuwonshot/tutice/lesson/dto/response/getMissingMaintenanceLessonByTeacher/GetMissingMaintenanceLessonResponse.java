package gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenanceLessonByTeacher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMissingMaintenanceLessonResponse {
    private List<MissingMaintenanceLesson> missingMaintenanceLessonList;

    public static GetMissingMaintenanceLessonResponse of(List<MissingMaintenanceLesson> missingMaintenanceLessonList) {
        return new GetMissingMaintenanceLessonResponse(missingMaintenanceLessonList);

    }
}

