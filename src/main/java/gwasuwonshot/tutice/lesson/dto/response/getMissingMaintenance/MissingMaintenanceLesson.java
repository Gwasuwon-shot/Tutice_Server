package gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenance;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MissingMaintenanceLesson {
    private Long idx;
    private String studentName;
    private String subject;
    private Long count;

    public static MissingMaintenanceLesson of(Long idx, String studentName, String subject, Long count) {
        return new MissingMaintenanceLesson(idx, studentName, subject, count);

    }
}
