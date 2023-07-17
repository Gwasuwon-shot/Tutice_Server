package gwasuwonshot.tutice.lesson.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
