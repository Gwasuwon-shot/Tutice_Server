package gwasuwonshot.tutice.lesson.dto.response.getMissingMaintenanceLessonByTeacher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
<<<<<<<< HEAD:src/main/java/gwasuwonshot/tutice/lesson/dto/response/getMissingMaintenanceLessonByTeacher/GetMissingMaintenanceLessonByTeacherResponse.java
public class GetMissingMaintenanceLessonByTeacherResponse {
    private List<GetMissingMaintenanceLesson> missingMaintenanceLessonList;
    public static GetMissingMaintenanceLessonByTeacherResponse of(List<GetMissingMaintenanceLesson> missingMaintenanceLessonList) {
        return new GetMissingMaintenanceLessonByTeacherResponse(missingMaintenanceLessonList);
========
public class GetMissingMaintenanceLessonResponse {
    private List<MissingMaintenanceLesson> missingMaintenanceLessonList;

    public static GetMissingMaintenanceLessonResponse of(List<MissingMaintenanceLesson> missingMaintenanceLessonList) {
        return new GetMissingMaintenanceLessonResponse(missingMaintenanceLessonList);
>>>>>>>> 793e03ae9413f984ce23a4a520e0c0da4dfb5bd2:src/main/java/gwasuwonshot/tutice/lesson/dto/response/getMissingMaintenanceLessonByTeacher/GetMissingMaintenanceLessonResponse.java

    }
}

