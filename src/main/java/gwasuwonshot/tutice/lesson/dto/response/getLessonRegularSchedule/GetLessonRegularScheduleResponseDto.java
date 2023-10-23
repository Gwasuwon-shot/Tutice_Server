package gwasuwonshot.tutice.lesson.dto.response.getLessonRegularSchedule;

import gwasuwonshot.tutice.lesson.dto.response.GetLessonProgressResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonRegularScheduleResponseDto {

    private List<GetLessonRegularSchedule> regularScheduleList;
    public static GetLessonRegularScheduleResponseDto of(List<GetLessonRegularSchedule> regularScheduleList) {
        return new GetLessonRegularScheduleResponseDto(regularScheduleList);
    }
}
