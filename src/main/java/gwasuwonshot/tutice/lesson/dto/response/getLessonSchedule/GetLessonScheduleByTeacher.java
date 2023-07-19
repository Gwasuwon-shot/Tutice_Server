package gwasuwonshot.tutice.lesson.dto.response.getLessonSchedule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonScheduleByTeacher implements GetLessonScheduleByUser{
    private Long idx;
    private String studentName;
    private String subject;
    private Long count;
    private Long nowCount;
    private Long percent;

    public static GetLessonScheduleByTeacher of(Long idx,String studentName, String subject,
                                                Long count,Long nowCount, Long percent ) {
        return new GetLessonScheduleByTeacher(idx, studentName, subject,count,nowCount,percent);

    }
}

