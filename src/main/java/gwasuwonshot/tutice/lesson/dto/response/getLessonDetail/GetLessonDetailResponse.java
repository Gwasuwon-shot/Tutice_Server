package gwasuwonshot.tutice.lesson.dto.response.getLessonDetail;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetLessonDetailResponse {
    private Long idx;
    private String teacherName;
    private String studentName;
    private String startDate;
    private String payment;
    private Long amount;
    private String subject;
    private String lessonCode;

    public static GetLessonDetailResponse of(Lesson lesson) {
        return GetLessonDetailResponse.builder()
                .idx(lesson.getIdx())
                .teacherName(lesson.getTeacher().getName())
                .studentName(lesson.getStudentName())
                .startDate(DateAndTimeConvert.localDateConvertString(lesson.getStartDate()))
                .payment(lesson.getPayment().getValue())
                .amount(lesson.getAmount())
                .subject(lesson.getSubject())
                .lessonCode(lesson.createLessonCode())
                .build();
    }
}
