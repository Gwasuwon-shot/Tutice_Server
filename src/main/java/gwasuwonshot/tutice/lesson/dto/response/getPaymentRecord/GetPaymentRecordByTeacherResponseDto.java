package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecord;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordByTeacherResponseDto implements GetPaymentRecordByUserResponseDto{
    private GetPaymentRecordLessonByTeacher lesson;
    private String todayDate;
    private List<GetPaymentRecord> paymentRecordList;

    public static GetPaymentRecordByTeacherResponseDto of(GetPaymentRecordLessonByTeacher lesson, String todayDate, List<GetPaymentRecord> paymentRecordList) {
        return new GetPaymentRecordByTeacherResponseDto(lesson, todayDate, paymentRecordList);
    }

}

