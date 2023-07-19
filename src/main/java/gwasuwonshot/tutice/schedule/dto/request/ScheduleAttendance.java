package gwasuwonshot.tutice.schedule.dto.request;

import gwasuwonshot.tutice.common.resolver.enumValue.Enum;
import gwasuwonshot.tutice.schedule.entity.ScheduleStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ScheduleAttendance {
    @NotNull(message = "스케줄 idx가 없습니다.")
    @Min(1)
    private Long idx;

    @Enum(enumClass = ScheduleStatus.class, ignoreCase = true, message ="잘못된 status 값 입니다.")
    private String status;
}
