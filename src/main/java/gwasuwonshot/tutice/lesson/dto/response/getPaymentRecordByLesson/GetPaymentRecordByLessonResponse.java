package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordByLesson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordByLessonResponse {
    private Long idx;
    private String date;
    private Long amount;
    private Boolean status;

    public static GetPaymentRecordByLessonResponse of(Long idx, String date, Long amount, Boolean status) {
        return new GetPaymentRecordByLessonResponse(idx, date, amount, status);
    }
}
