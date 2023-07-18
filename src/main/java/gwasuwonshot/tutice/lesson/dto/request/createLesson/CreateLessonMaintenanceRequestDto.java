package gwasuwonshot.tutice.lesson.dto.request.createLesson;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "수업 연장 여부 api Dto")
public class CreateLessonMaintenanceRequestDto {

    @NotNull( message = "lessonIdx가 없습니다.")
    @Schema(description = "연장여부를 알릴 수업")
    private Long lessonIdx;


    @NotNull( message = "수업 연장 여부가 없습니다.")
    @Schema(description = "수업 연장 여부 flag")
    private Boolean isLessonMaintenance;
}
