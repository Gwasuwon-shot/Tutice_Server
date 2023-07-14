package gwasuwonshot.tutice.lesson.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonDetailByParentsResponseDto {

    private GetLessonDetailByParentsResponseLesson lesson;
    private String startDate;
    private String payment;
    private Long amount;

    public static GetLessonDetailByParentsResponseDto of(GetLessonDetailByParentsResponseLesson lesson,
                                                         String startDate, String payment, Long amount) {
        return new GetLessonDetailByParentsResponseDto(lesson, startDate,payment,amount);
    }

}



