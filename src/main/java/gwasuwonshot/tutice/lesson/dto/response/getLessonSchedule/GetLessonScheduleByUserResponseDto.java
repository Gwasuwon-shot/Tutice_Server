package gwasuwonshot.tutice.lesson.dto.response.getLessonSchedule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetLessonScheduleByUserResponseDto {
    private GetLessonScheduleByUser lesson;
    private List<GetLessonSchedule> scheduleList;

    public static GetLessonScheduleByUserResponseDto of(GetLessonScheduleByUser lesson,
                                                        List<GetLessonSchedule> scheduleList){
        return new GetLessonScheduleByUserResponseDto(lesson, scheduleList);

    }
}
