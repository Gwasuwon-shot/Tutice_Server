package gwasuwonshot.tutice.lesson.exception.invalid;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import lombok.Getter;


@Getter
public class InvalidTimeException extends BasicException {
    public InvalidTimeException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }


}