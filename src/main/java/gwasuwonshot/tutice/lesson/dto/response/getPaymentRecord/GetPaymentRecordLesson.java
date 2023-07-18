package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecord;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordLesson {
    private Long idx;
    private String studentName;
    private String subject;

    public static GetPaymentRecordLesson of(Long idx,String studentName,String subject) {
        return new GetPaymentRecordLesson(idx, studentName, subject);
    }
}
