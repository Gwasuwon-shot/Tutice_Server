package gwasuwonshot.tutice.schedule.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class InvalidAttendanceStatusException extends BasicException {
    public InvalidAttendanceStatusException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}