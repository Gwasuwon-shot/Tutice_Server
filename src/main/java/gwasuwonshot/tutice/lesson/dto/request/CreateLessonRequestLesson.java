package gwasuwonshot.tutice.lesson.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Schema(description = "선불 or 후불")
    private String payment;

    @NotBlank
    @Schema(description = "과외 금액")
    private Long amount;

    @NotBlank
    @Schema(description = "과외 회차")
    private Long count;


    @NotBlank
    @Schema(description = "수업 시작날짜")
    @Pattern(
            regexp="/^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/", //날짜 형식
            message = "날짜의 형태는 yyyy-mm-dd 형태여야합니다."
    )
    private String startDate;

    @NotNull
    @Schema(description = "수업의 정기 일정")
    private List<CreateLessonRequestRegularSchedule> regularScheduleList;


}
