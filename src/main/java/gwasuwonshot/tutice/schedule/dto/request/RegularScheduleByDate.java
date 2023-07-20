package gwasuwonshot.tutice.schedule.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegularScheduleByDate {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
