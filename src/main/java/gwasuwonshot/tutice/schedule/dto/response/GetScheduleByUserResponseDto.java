package gwasuwonshot.tutice.schedule.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetScheduleByUserResponseDto {
    private List<ScheduleByDate> scheduleList;

    public static GetScheduleByUserResponseDto of() {
        return GetScheduleByUserResponseDto.builder()
                .build();
    }

    public static GetScheduleByUserResponseDto ofDailySchedule(List<ScheduleByDate> dailyScheduleList) {
        return GetScheduleByUserResponseDto.builder()
                .scheduleList(dailyScheduleList)
                .build();
    }
}
