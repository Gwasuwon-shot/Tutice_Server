package gwasuwonshot.tutice.schedule.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class InvalidScheduleException extends BasicException {
    public InvalidScheduleException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}