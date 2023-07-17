package gwasuwonshot.tutice.lesson.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMissingMaintenanceLessonDto {
    private List<GetMissingMaintenanceLesson> missingMaintenanceLessonList;
    public static GetMissingMaintenanceLessonDto of(List<GetMissingMaintenanceLesson> missingMaintenanceLessonList) {
        return new GetMissingMaintenanceLessonDto(missingMaintenanceLessonList);

    }
}

