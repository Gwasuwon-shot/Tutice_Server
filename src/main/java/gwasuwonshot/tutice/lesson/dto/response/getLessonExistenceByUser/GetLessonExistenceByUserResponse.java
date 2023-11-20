package gwasuwonshot.tutice.lesson.dto.response.getLessonExistenceByUser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonExistenceByUserResponse {
    private Boolean isLesson;
    private String userName;

    public static GetLessonExistenceByUserResponse of(Boolean isLesson, String userName) {
        return new GetLessonExistenceByUserResponse(isLesson, userName);
    }
}
