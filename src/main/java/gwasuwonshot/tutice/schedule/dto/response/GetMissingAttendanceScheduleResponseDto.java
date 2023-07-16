package gwasuwonshot.tutice.schedule.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetMissingAttendanceScheduleResponseDto {
    private List<MissingScheduleByDate> missingAttendanceList;


    public static GetMissingAttendanceScheduleResponseDto ofSchedule(List<MissingScheduleByDate> missingAttendanceList) {
        return GetMissingAttendanceScheduleResponseDto.builder()
                .missingAttendanceList(missingAttendanceList)
                .build();
    }

    public static GetMissingAttendanceScheduleResponseDto of() {
        return GetMissingAttendanceScheduleResponseDto.builder()
                .build();
    }
}
