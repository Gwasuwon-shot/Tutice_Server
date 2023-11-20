package gwasuwonshot.tutice.lesson.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "레슨코드로 학부모 연결 Dto")
public class UpdateLessonParentsRequest {
    @NotBlank( message = "lessonCode가 없습니다.")
    @Schema(description = "lessonCode")
    private String lessonCode;
}
