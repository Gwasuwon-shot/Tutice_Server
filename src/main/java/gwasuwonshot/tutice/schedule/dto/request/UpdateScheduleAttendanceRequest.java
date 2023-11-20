package gwasuwonshot.tutice.schedule.dto.request;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateScheduleAttendanceRequest {
    @Valid
    @NotNull(message = "스케줄 정보가 없습니다.")
    private ScheduleAttendance schedule;
}
