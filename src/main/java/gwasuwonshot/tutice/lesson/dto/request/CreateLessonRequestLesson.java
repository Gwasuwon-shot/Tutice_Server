package gwasuwonshot.tutice.lesson.dto.request;

import gwasuwonshot.tutice.common.resolver.enumValue.Enum;
import gwasuwonshot.tutice.lesson.entity.Payment;
import gwasuwonshot.tutice.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateLessonRequestLesson {
    @NotBlank
    @Schema(description = "학생 이름")
    private String studentName;

    @NotBlank
    @Schema(description = "과목 이름")
    private String subject;

    @NotBlank
    // TODO :  enum validation으로 변경
    @Pattern(regexp = "(선불|후불)", message = "payment는 선불 or 후불 입니다.")
    @Schema(description = "선불 or 후불")
    private String payment;

    @NotNull
    @Min(1)
    @Schema(description = "과외 금액")
    private Long amount;

    @NotNull
    @Schema(description = "과외 회차")
    private Long count;


    @NotBlank
    @Schema(description = "수업 시작날짜")
    // TODO :  날짜 추후 validate 확실하게

//    @Pattern(//문제
//            regexp="/(^\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/", //날짜 형식
//            message = "날짜의 형태는 yyyy-mm-dd 형태여야합니다."
//    )
    private String startDate;


    @Valid
    @NotNull
    @Size(min=1)
    @Schema(description = "수업의 정기 일정")
    private List<CreateLessonRequestRegularSchedule> regularScheduleList;


}
