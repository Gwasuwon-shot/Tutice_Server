package gwasuwonshot.tutice.schedule.dto.response.getTemporarySchedule;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetTemporaryScheduleResponse {
    private List<TemporarySchedule> temporaryScheduleList;


    public static GetTemporaryScheduleResponse of(List<TemporarySchedule> temporaryScheduleList) {
        return GetTemporaryScheduleResponse.builder()
                .temporaryScheduleList(temporaryScheduleList)
                .build();
    }
}
