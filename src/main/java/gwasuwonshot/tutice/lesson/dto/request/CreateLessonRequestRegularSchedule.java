package gwasuwonshot.tutice.lesson.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateLessonRequestRegularSchedule {
    @NotBlank
    @Schema(description = "요일")
    @Pattern(
            regexp="(월|화|수|목|금|토|일)", //요일 정보
            message = "요일은 월,화,수,목,금,토,일 중 하나여야 합니다."
    )
    private String dayOfWeek;

    @NotBlank
    @Schema(description = "시작 시간")
    @Pattern(
            regexp="(0[0-9]|1[0-9]|2[0-4]):(0[1-9]|[0-5][0-9])", //시간정보
            message = "시간의 형태는 HH:mm의 형태여야합니다."
    )
    private String startTime;

    @NotBlank
    @Schema(description = "종료 시간")
    @Pattern(
            regexp="(0[0-9]|1[0-9]|2[0-4]):(0[1-9]|[0-5][0-9])", //시간정보
            message = "시간의 형태는 HH:mm의 형태여야합니다."
    )
    private String endTime;
}

