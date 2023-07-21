package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetTemporaryScheduleResponseDto {
    private List<TemporarySchedule> temporaryScheduleList;


    public static GetTemporaryScheduleResponseDto of(List<TemporarySchedule> temporaryScheduleList) {
        return GetTemporaryScheduleResponseDto.builder()
                .temporaryScheduleList(temporaryScheduleList)
                .build();
    }
}
