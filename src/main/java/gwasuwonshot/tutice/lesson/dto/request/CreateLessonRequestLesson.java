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
    @NotBlank(message = "학생이름이 없습니다.")
    @Schema(description = "학생 이름")
    private String studentName;

    @NotBlank(message = "과목이름이 없습니다.")
    @Schema(description = "과목 이름")
    private String subject;

    @NotBlank(message = "결재방식 정보가 없습니다.")
    // TODO :  enum validation으로 변경
    @Enum(enumClass = Payment.class, ignoreCase = true, message ="payment는 선불 or 후불 입니다.")
    @Schema(description = "선불 or 후불")
    private String payment;

    @NotNull(message = "금액 정보가 없습니다.")
    @Schema(description = "과외 금액")
    private Long amount;

    @NotNull(message = "회차 정보가 없습니다.")
    @Schema(description = "과외 회차")
    @Min(1)
    private Long count;


    @NotBlank(message = "시작날짜가 없습니다.")
    @Schema(description = "수업 시작날짜")
    // TODO :  날짜 추후 validate 확실하게

//    @Pattern(//문제
//            regexp="/(^\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/", //날짜 형식
//            message = "날짜의 형태는 yyyy-mm-dd 형태여야합니다."
//    )
    private String startDate;


    @Valid
    @NotNull(message = "정기일정이 없습니다.")
    @Size(min=1, message = "정기일정은 한개이상 필요합니다.")
    @Schema(description = "수업의 정기 일정")
    private List<CreateLessonRequestRegularSchedule> regularScheduleList;


}
