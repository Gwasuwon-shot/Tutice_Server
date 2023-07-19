package gwasuwonshot.tutice.user.exception.userException;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class NotAllowedNotificationException extends BasicException {
    public NotAllowedNotificationException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }

}
