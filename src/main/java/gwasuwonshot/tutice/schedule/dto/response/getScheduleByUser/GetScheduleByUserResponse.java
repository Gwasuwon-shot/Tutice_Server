package gwasuwonshot.tutice.schedule.dto.response.getScheduleByUser;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetScheduleByUserResponse {
    private List<ScheduleByDate> scheduleList;

    public static GetScheduleByUserResponse of() {
        return GetScheduleByUserResponse.builder()
                .build();
    }

    public static GetScheduleByUserResponse ofDailySchedule(List<ScheduleByDate> dailyScheduleList) {
        return GetScheduleByUserResponse.builder()
                .scheduleList(dailyScheduleList)
                .build();
    }
}
