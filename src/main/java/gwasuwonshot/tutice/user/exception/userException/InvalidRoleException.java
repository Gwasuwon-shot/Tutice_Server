package gwasuwonshot.tutice.user.exception.userException;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class InvalidRoleException extends BasicException {
    public InvalidRoleException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }

}
