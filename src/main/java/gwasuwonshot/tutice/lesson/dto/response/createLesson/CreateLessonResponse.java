package gwasuwonshot.tutice.lesson.dto.response.createLesson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateLessonResponse {
    private String lessonCode;
    private Long paymentRecordIdx;
    private Long lessonidx;

    public static CreateLessonResponse of(String lessonCode, Long paymentRecordIdx, Long lessonIdx) {
        return new CreateLessonResponse(lessonCode, paymentRecordIdx, lessonIdx);
    }

}
