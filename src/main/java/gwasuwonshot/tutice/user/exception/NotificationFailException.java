package gwasuwonshot.tutice.user.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class NotificationFailException extends BasicException {
    public NotificationFailException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }

}
