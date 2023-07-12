package gwasuwonshot.tutice.lesson.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateLessonRequestRegularSchedule {
    @NotBlank
    @Schema(description = "요일")
    private String dayOfWeek;

    @NotBlank
    @Schema(description = "시작 시간")
    private String startTime;

    @NotBlank
    @Schema(description = "종료 시간")
    private String endTime;
}

