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

    public static GetLessonDetailByParentsResponseDto of(Long idx, String teacherName, String startDate, String payment,
                                                         Long amount, GetLessonDetailByParentsResponseAccount account) {
        return new GetLessonDetailByParentsResponseDto(idx, teacherName, startDate,payment, amount, account);

    }

}



