package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordView;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordViewResponseDto {
    private GetPaymentRecordViewLesson lesson;
    private String paymentDate;

    public static GetPaymentRecordViewResponseDto of(GetPaymentRecordViewLesson lesson, String paymentDate) {
        return new GetPaymentRecordViewResponseDto(lesson, paymentDate);
    }

}

