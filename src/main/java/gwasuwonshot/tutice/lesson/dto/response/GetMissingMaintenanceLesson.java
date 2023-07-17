package gwasuwonshot.tutice.lesson.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMissingMaintenanceLesson {
    private MissingMaintenanceLesson lesson;
    private String endScheduleDate; //"2023-05-25"

    public static GetMissingMaintenanceLesson of(MissingMaintenanceLesson lesson, String endScheduleDate) {
        return new GetMissingMaintenanceLesson(lesson, endScheduleDate);

    }
}
