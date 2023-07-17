package gwasuwonshot.tutice.schedule.entity;

import gwasuwonshot.tutice.common.resolver.enumValue.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ScheduleStatus implements EnumModel {

    NO_STATUS("SCHEDULE_STATUS_NO_STATUS","상태없음"),
    ATTENDANCE("SCHEDULE_STATUS_ATTENDANCE","출석"),
    ABSENCE("SCHEDULE_STATUS_ABSENCE","결석"),
    CANCLE("SCHEDULE_STATUS_CANCLE", "취소");

    private final String key;
    private final String value;

    public static ScheduleStatus getScheduleStatusByValue(String value){
        return Arrays.stream(ScheduleStatus.values())
                .filter(s -> s.getValue().equals(value))
                .findAny().orElseThrow();
    }

    public static List<ScheduleStatus> getAttendanceScheduleStatusList(){
        return new ArrayList<>(Arrays.asList(ScheduleStatus.ABSENCE, ScheduleStatus.ATTENDANCE));
    }
}
