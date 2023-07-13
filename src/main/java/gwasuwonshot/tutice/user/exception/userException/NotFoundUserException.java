package gwasuwonshot.tutice.user.exception.userException;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import lombok.Getter;

@Getter
public class NotFoundUserException extends BasicException {
    public NotFoundUserException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }


}
