package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordCycle;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetPaymentRecordCycleResponse {
    private Long idx;
    private Long cycle;
    private String startDate;
    private String endDate;

    public static GetPaymentRecordCycleResponse of(Long idx, Long cycle, Schedule startSchedule, Schedule endSchedule) {
        return GetPaymentRecordCycleResponse.builder()
                .idx(idx)
                .cycle(cycle)
                .startDate(DateAndTimeConvert.localDateConvertString(startSchedule.getDate()))
                .endDate(DateAndTimeConvert.localDateConvertString(endSchedule.getDate()))
                .build();
    }
}
