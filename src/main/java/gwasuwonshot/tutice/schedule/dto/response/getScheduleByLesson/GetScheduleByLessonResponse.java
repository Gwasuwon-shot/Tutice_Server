package gwasuwonshot.tutice.schedule.dto.response.getScheduleByLesson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetScheduleByLessonResponse {
    private Long idx;
    private String date;
    private String status;
    private String startTime;
    private String endTime;

    public static GetScheduleByLessonResponse of(Long idx, String date, String status, String startTime, String endTime) {
        return new GetScheduleByLessonResponse(idx, date, status,startTime,endTime);

    }
}
