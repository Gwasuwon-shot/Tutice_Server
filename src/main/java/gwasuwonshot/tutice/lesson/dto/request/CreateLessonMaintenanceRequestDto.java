package gwasuwonshot.tutice.lesson.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "수업 연장 여부 api Dto")
public class CreateLessonMaintenanceRequestDto {

    @NotBlank( message = "lessonIdx가 없습니다.")
    @Schema(description = "연장여부를 알릴 수업")
    private Long lessonIdx;


    @NotBlank( message = "수업 연장 여부가 없습니다.")
    @Schema(description = "수업 연장 여부 flag")
    private Boolean isLessonMaintenance;
}
