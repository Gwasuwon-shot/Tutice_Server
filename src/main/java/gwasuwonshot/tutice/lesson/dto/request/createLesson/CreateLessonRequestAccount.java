package gwasuwonshot.tutice.lesson.dto.request.createLesson;

import gwasuwonshot.tutice.common.resolver.enumValue.Enum;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;
import gwasuwonshot.tutice.user.entity.Bank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateLessonRequestAccount {
    @NotBlank(message = "예금주가 없습니다.")
    @Schema(description = "예금주")
    private String name;

    @NotBlank(message = "은행이 없습니다.")
    @Schema(description = "은행")
    @Enum(enumClass = Bank.class, ignoreCase = true, message ="은행 값이 유효하지 않습니다.")
    private String bank;

    @NotBlank(message = "계좌번호가 없습니다.")
    // TODO :  계좌번호 추후 validate 확실하게
//    @Pattern(//문제
//            regexp="[0123456789-]", //계좌번호
//            message = "계좌번호는 숫자와 하이픈으로만 이루어져야합니다."
//    )
    @Schema(description = "계좌번호")
    private String number;

}
