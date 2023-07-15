package gwasuwonshot.tutice.user.exception.authException;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import lombok.Getter;

@Getter
public class NullAccessTokenException extends BasicException {
    public NullAccessTokenException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
