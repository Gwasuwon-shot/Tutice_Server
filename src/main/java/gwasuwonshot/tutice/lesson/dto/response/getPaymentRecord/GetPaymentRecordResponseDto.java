package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecord;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordResponseDto {
    private Long idx;
    private String date;
    private Long amount;
    private Boolean status;

    public static GetPaymentRecordResponseDto of(Long idx, String date, Long amount, Boolean status) {
        return new GetPaymentRecordResponseDto(idx, date, amount, status);
    }
}
