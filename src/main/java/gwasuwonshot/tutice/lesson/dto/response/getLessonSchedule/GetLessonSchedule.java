package gwasuwonshot.tutice.lesson.dto.response.getLessonSchedule;

import gwasuwonshot.tutice.lesson.dto.response.getLessonDetail.GetLessonDetailByParentsResponseAccount;
import gwasuwonshot.tutice.lesson.dto.response.getLessonDetail.GetLessonDetailByParentsResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonSchedule {
    private Long idx;
    private String date;
    private String status;
    private String startTime;
    private String endTime;

    public static GetLessonSchedule of(Long idx, String date, String status, String startTime, String endTime) {
        return new GetLessonSchedule(idx, date, status,startTime,endTime);

    }
}
