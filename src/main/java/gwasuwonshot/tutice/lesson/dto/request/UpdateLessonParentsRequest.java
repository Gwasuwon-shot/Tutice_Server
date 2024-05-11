package gwasuwonshot.tutice.lesson.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "학부모 번호로 수업 연결Dto")
public class UpdateLessonParentsRequest {

    @NotNull( message = "lessonIdx가 없습니다.")
    @Schema(description = "학부모를 연결할 수업")
    private Long lessonIdx;

    @NotBlank( message = "학부모 번호가 없습니다.")
    @Pattern(regexp = "^01(0|1|[6-9])[0-9]{3,4}[0-9]{4}$",
            message = "올바른 전화번호를 입력해주세요.")
    @Schema(description = "parentsPhone")
    private String parentsPhone;
}
