package gwasuwonshot.tutice.lesson.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonExistenceByUserResponseDto {
    private Boolean isLesson;
    private String userName;

    public static GetLessonExistenceByUserResponseDto of(Boolean isLesson, String userName) {
        return new GetLessonExistenceByUserResponseDto(isLesson, userName);
    }

}
