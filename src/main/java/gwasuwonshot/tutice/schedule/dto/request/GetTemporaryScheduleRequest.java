package gwasuwonshot.tutice.schedule.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetTemporaryScheduleRequest {
    private String studentName;
    private String subject;
    private Long count;
    private String startDate;
    private List<RegularScheduleByDate> regularScheduleList;
}
