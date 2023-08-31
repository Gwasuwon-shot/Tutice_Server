package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetLatestScheduleByTeacherResponseDto {
    private TodayResponseDto latestScheduleDay;
    private List<LatestSchedule> latestScheduleList;

    public static GetLatestScheduleByTeacherResponseDto of(LocalDate date, List<Schedule> latestScheduleList) {
        return GetLatestScheduleByTeacherResponseDto.builder()
                .latestScheduleDay(TodayResponseDto.of(DateAndTimeConvert.localDateConvertString(date), DateAndTimeConvert.localDateConvertDayOfWeek(date)))
                .latestScheduleList(latestScheduleList.stream().map(s -> LatestSchedule.of(s.getLesson(), s)).collect(Collectors.toList()))
                .build();
    }
}
