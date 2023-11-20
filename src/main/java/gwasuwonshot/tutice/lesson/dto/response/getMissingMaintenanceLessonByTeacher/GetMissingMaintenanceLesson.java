package gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenanceLessonByTeacher;

import gwasuwonshot.tutice.lesson.dto.response.LessonResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMissingMaintenanceLesson {
    private LessonResponse lesson;
    private String endScheduleDate; //"2023-05-25"

    public static GetMissingMaintenanceLesson of(LessonResponse lesson, String endScheduleDate) {
        return new GetMissingMaintenanceLesson(lesson, endScheduleDate);

    }
}
