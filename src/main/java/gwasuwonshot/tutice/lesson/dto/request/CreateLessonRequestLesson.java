package gwasuwonshot.tutice.lesson.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private String startDate;

    @NotNull
    @Schema(description = "수업의 정기 일정")
    private List<CreateLessonRequestRegularSchedule> regularScheduleList;


}
