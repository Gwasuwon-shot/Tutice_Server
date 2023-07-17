package gwasuwonshot.tutice.lesson.dto.request.createLesson;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "수업 생성 DTO")
public class CreateLessonRequestDto {
    @Valid
    @NotNull(message = "수업 정보가 없습니다.")
    @Schema(description = "수업 자체 정보")
    private CreateLessonRequestLesson lesson;


    @Valid
    @NotNull(message = "계좌 정보가 없습니다.")
    @Schema(description = "선생님 계좌 정보")
    private CreateLessonRequestAccount account;

}
