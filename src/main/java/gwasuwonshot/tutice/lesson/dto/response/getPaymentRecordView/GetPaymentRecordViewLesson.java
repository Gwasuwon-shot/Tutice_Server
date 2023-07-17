package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecordView;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordViewLesson {
    private Long idx;
    private String studentName;
    private String subject;
    private GetPaymentRecordViewCycle cycle;


    public static GetPaymentRecordViewLesson of(Long idx, String studentName, String subject, GetPaymentRecordViewCycle cycle ) {
        return new GetPaymentRecordViewLesson(idx,studentName, subject,cycle);
    }
}

