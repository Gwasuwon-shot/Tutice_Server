package gwasuwonshot.tutice.schedule.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class InvalidScheduleDateException extends BasicException {
    public InvalidScheduleDateException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}