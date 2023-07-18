package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecord;


import gwasuwonshot.tutice.lesson.dto.response.CreateLessonResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordByTeacherResponseDto {
    private GetPaymentRecordLesson lesson;
    private String todayDate;
    private List<GetPaymentRecord> paymentRecordList;

    public static GetPaymentRecordByTeacherResponseDto of(GetPaymentRecordLesson lesson, String todayDate,List<GetPaymentRecord> paymentRecordList) {
        return new GetPaymentRecordByTeacherResponseDto(lesson, todayDate, paymentRecordList);
    }

}

