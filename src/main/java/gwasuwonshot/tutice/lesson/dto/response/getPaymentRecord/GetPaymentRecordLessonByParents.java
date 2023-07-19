package gwasuwonshot.tutice.lesson.dto.response.getPaymentRecord;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPaymentRecordLessonByParents {
    private Long idx;
    private String studentName;
    private String teacherName;
    private String subject;

    public static GetPaymentRecordLessonByParents of(Long idx, String studentName,String teacherName, String subject) {
        return new GetPaymentRecordLessonByParents(idx, studentName,teacherName ,subject);
    }



}
