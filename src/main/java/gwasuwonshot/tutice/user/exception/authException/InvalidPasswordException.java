package gwasuwonshot.tutice.user.exception.authException;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import lombok.Getter;

@Getter
public class InvalidPasswordException extends BasicException {
    public InvalidPasswordException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
