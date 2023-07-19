package gwasuwonshot.tutice.schedule.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class NotFoundScheduleException extends BasicException {
    public NotFoundScheduleException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}