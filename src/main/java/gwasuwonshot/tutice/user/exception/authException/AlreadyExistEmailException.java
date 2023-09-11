package gwasuwonshot.tutice.user.exception.authException;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import lombok.Getter;

@Getter
public class AlreadyExistEmailException extends BasicException {
    public AlreadyExistEmailException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
