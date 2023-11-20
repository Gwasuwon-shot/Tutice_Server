package gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenanceLessonByTeacher;

import gwasuwonshot.tutice.lesson.dto.response.LessonResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MissingMaintenanceLesson {
    private LessonResponse lesson;
    private String endScheduleDate;

    public static MissingMaintenanceLesson of(LessonResponse lesson, String endScheduleDate) {
        return new MissingMaintenanceLesson(lesson, endScheduleDate);

    }
}
