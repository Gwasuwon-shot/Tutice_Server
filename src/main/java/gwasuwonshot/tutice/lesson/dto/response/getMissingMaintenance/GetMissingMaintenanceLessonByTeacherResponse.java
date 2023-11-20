package gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenance;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMissingMaintenanceLessonByTeacherResponse {
    private List<GetMissingMaintenanceLesson> missingMaintenanceLessonList;
    public static GetMissingMaintenanceLessonByTeacherResponse of(List<GetMissingMaintenanceLesson> missingMaintenanceLessonList) {
        return new GetMissingMaintenanceLessonByTeacherResponse(missingMaintenanceLessonList);

    }
}

