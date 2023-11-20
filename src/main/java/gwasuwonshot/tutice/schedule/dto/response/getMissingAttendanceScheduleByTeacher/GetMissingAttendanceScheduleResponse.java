package gwasuwonshot.tutice.schedule.dto.response.getMissingAttendanceScheduleByTeacher;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetMissingAttendanceScheduleResponse {
    private List<MissingScheduleByDate> missingAttendanceList;


    public static GetMissingAttendanceScheduleResponse ofSchedule(List<MissingScheduleByDate> missingAttendanceList) {
        return GetMissingAttendanceScheduleResponse.builder()
                .missingAttendanceList(missingAttendanceList)
                .build();
    }

    public static GetMissingAttendanceScheduleResponse of() {
        return GetMissingAttendanceScheduleResponse.builder()
                .build();
    }
}
