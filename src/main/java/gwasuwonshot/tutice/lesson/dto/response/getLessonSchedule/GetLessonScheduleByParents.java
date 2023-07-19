package gwasuwonshot.tutice.lesson.dto.response.getLessonSchedule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonScheduleByParents implements GetLessonScheduleByUser{
    private Long idx;
    private String studentName;
    private String teacherName;
    private String subject;
    private Long count;
    private Long nowCount;
    private Long percent;

    public static GetLessonScheduleByParents of(Long idx,String studentName,String teacherName,
                                                String subject, Long count,Long nowCount, Long percent ) {
        return new GetLessonScheduleByParents(idx, studentName, teacherName,subject,count,nowCount,percent);

    }
}
