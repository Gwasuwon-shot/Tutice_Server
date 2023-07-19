package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecord;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordLessonByTeacher {
    private Long idx;
    private String studentName;
    private String subject;

    public static GetPaymentRecordLessonByTeacher of(Long idx, String studentName, String subject) {
        return new GetPaymentRecordLessonByTeacher(idx, studentName, subject);
    }
}
