package gwasuwonshot.tutice.schedule.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateScheduleRequestDto {
    @Valid
    @NotNull(message = "스케줄 정보가 없습니다.")
    private UpdateScheduleRequestSchedule schedule;
}
