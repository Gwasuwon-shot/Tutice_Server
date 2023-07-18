package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordView;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordViewCycle {
    private Long value;
    private String startDate;
    private String endDate;

    public static GetPaymentRecordViewCycle of(Long value, String startDate, String endDate) {
        return new GetPaymentRecordViewCycle(value, startDate, endDate);
    }
}
