package gwasuwonshot.tutice.schedule.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateScheduleRequestSchedule {
    @NotNull(message = "스케줄 idx가 없습니다.")
    @Min(1)
    private Long idx;

    // TODO 날짜 유효성 검사
    @NotBlank(message = "날짜가 없습니다.")
    private String date;

    // TODO 시간 유효성 검사
    @NotBlank(message = "시작 시간이 없습니다.")
    private String startTime;

    // TODO 시간 유효성 검사
    @NotBlank(message = "종료 시간이 없습니다.")
    private String endTime;
}
