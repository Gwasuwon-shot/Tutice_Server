package gwasuwonshot.tutice.user.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import lombok.Getter;

@Getter
public class UserNotFoundException extends BasicException {
    public UserNotFoundException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }


}
