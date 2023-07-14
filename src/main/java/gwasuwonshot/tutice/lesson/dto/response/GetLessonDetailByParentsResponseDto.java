package gwasuwonshot.tutice.lesson.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonDetailByParentsResponseDto {

    private Long idx;
    private String teacherName;
    private String startDate;
    private String payment;
    private Long amount;
    private GetLessonDetailByParentsResponseAccount account;

    // TODO : 근데 이런 도메인 클래스를 dto 에서 그대로 쓰면 결합도가 높아지는게 아닌지?
    public static GetLessonDetailByParentsResponseDto of(Lesson lesson) {
        return new GetLessonDetailByParentsResponseDto(lesson.getIdx(),
                lesson.getTeacher().getName(),
                DateAndTimeConvert.dateConvertString(lesson.getStartDate()),
                lesson.getPayment().getValue(),
                lesson.getAmount(),
                GetLessonDetailByParentsResponseAccount.of(lesson.getAccount()));
    }

}



