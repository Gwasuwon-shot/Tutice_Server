package gwasuwonshot.tutice.user.exception.userException;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class ForbiddenNotificationUserException extends BasicException {
    public ForbiddenNotificationUserException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }

}
