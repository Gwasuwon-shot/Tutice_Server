package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecord;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordByParentsResponseDto implements GetPaymentRecordByUserResponseDto{
    private GetPaymentRecordLessonByParents lesson;
    private String todayDate;
    private List<GetPaymentRecord> paymentRecordList;

    public static GetPaymentRecordByParentsResponseDto of(GetPaymentRecordLessonByParents lesson, String todayDate, List<GetPaymentRecord> paymentRecordList) {
        return new GetPaymentRecordByParentsResponseDto(lesson, todayDate, paymentRecordList);
    }
}

