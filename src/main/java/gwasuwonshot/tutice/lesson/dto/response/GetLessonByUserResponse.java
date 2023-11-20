package gwasuwonshot.tutice.lesson.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonByUserResponse {
    private Boolean isLesson;
    private String userName;

    public static GetLessonByUserResponse of(Boolean isLesson, String userName) {
        return new GetLessonByUserResponse(isLesson, userName);
    }

}
