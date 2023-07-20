package gwasuwonshot.tutice.schedule.dto.response;

import gwasuwonshot.tutice.schedule.entity.Schedule;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetTemporaryScheduleResponseDto {
    private String studentName;
    private String subject;
    private List<ScheduleByDateAndTime> regularScheduleList;

    public static GetTemporaryScheduleResponseDto of(String studentName, String subject, List<Schedule> scheduleList) {
        return GetTemporaryScheduleResponseDto.builder()
                .studentName(studentName)
                .subject(subject)
                .regularScheduleList(scheduleList.stream()
                        .map(s -> ScheduleByDateAndTime.of(s.getDate(), s.getStartTime(), s.getEndTime()))
                        .collect(Collectors.toList()))
                .build();
    }
}
