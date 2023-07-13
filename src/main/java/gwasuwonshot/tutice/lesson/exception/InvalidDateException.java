package gwasuwonshot.tutice.lesson.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;
import lombok.Getter;

@Getter
public class InvalidDateException extends BasicException {
    public InvalidDateException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }


}