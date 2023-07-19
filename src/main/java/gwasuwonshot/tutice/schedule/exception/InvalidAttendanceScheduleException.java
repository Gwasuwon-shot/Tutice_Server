package gwasuwonshot.tutice.schedule.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class InvalidAttendanceScheduleException extends BasicException {
    public InvalidAttendanceScheduleException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}