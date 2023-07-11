package gwasuwonshot.tutice.schedule.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleStatus {

    NO_STATUS("SCHEDULE_STATUS_NO_STATUS","상태없음"),
    ATTENDANCE("SCHEDULE_STATUS_ATTENDANCE","출석"),
    ABSENCE("SCHEDULE_STATUS_ABSENCE","결석"),
    CANCLE("SCHEDULE_STATUS_CANCLE", "취소");

    private final String key;
    private final String value;
}
