package gwasuwonshot.tutice.lesson.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateLessonRequestAccount {
    @NotBlank
    @Schema(description = "예금주")
    private String name;

    @NotBlank
    @Schema(description = "은행")
    private String bank;

    @NotBlank
    @Pattern(
            regexp="([0-9,\\-]{3,6}\\-[0-9,\\-]{2,6}\\-[0-9,\\-])", //계좌번호
            message = "계좌번호는 숫자와 하이픈으로만 이루어져야합니다."
    )
    @Schema(description = "계좌번호")
    private String number;

}
