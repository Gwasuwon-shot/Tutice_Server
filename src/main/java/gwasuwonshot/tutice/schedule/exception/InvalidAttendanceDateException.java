package gwasuwonshot.tutice.schedule.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class InvalidAttendanceDateException extends BasicException {
    public InvalidAttendanceDateException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}