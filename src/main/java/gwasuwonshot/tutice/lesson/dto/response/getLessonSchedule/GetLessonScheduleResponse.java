package gwasuwonshot.tutice.lesson.dto.response.getLessonSchedule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonScheduleResponse {
    private Long idx;
    private String date;
    private String status;
    private String startTime;
    private String endTime;

    public static GetLessonScheduleResponse of(Long idx, String date, String status, String startTime, String endTime) {
        return new GetLessonScheduleResponse(idx, date, status,startTime,endTime);

    }
}
