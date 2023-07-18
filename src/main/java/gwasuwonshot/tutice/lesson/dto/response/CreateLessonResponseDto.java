package gwasuwonshot.tutice.lesson.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateLessonResponseDto {
    private String lessonCode;
    private Long paymentRecordIdx;

    public static CreateLessonResponseDto of(String lessonCode, Long paymentRecordIdx) {
        return new CreateLessonResponseDto(lessonCode, paymentRecordIdx);
    }


}
