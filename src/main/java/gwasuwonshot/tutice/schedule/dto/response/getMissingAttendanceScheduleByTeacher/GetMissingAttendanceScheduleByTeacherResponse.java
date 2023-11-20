package gwasuwonshot.tutice.schedule.dto.response.getMissingAttendanceScheduleByTeacher;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetMissingAttendanceScheduleByTeacherResponse {
    private List<MissingScheduleByDate> missingAttendanceList;


    public static GetMissingAttendanceScheduleByTeacherResponse ofSchedule(List<MissingScheduleByDate> missingAttendanceList) {
        return GetMissingAttendanceScheduleByTeacherResponse.builder()
                .missingAttendanceList(missingAttendanceList)
                .build();
    }

    public static GetMissingAttendanceScheduleByTeacherResponse of() {
        return GetMissingAttendanceScheduleByTeacherResponse.builder()
                .build();
    }
}
