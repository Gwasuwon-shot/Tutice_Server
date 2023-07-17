package gwasuwonshot.tutice.lesson.dto.response.getLessonByParents;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonByParents {
    private Long idx;
    private String teacherName;
    private String studentName;
    private String subject;
    private Long count;
    private Long nowCount;
    private Long percent;

    public static GetLessonByParents of(Long idx,String teacherName,String studentName,
                                        String subject, Long count, Long nowCount ,Long percent) {
        return new GetLessonByParents(idx,teacherName,studentName,subject,count,nowCount,percent);

    }
}
