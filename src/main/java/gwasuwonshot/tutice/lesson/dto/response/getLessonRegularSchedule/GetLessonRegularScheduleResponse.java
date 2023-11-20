package gwasuwonshot.tutice.lesson.dto.response.getLessonRegularSchedule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonRegularScheduleResponse {

    private List<LessonRegularSchedule> regularScheduleList;

    public static GetLessonRegularScheduleResponse of(List<LessonRegularSchedule> regularScheduleList) {
        return new GetLessonRegularScheduleResponse(regularScheduleList);
    }
}
