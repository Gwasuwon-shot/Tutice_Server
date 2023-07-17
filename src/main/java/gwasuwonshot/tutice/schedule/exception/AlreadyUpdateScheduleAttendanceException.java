package gwasuwonshot.tutice.schedule.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class AlreadyUpdateScheduleAttendanceException extends BasicException {
    public AlreadyUpdateScheduleAttendanceException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
