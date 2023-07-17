package gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenance;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMissingMaintenanceLessonResponseDto {
    private List<GetMissingMaintenanceLesson> missingMaintenanceLessonList;
    public static GetMissingMaintenanceLessonResponseDto of(List<GetMissingMaintenanceLesson> missingMaintenanceLessonList) {
        return new GetMissingMaintenanceLessonResponseDto(missingMaintenanceLessonList);

    }
}

