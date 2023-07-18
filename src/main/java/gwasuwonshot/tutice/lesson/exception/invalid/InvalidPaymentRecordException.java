package gwasuwonshot.tutice.lesson.exception.invalid;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class InvalidPaymentRecordException extends BasicException {
    public InvalidPaymentRecordException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }


}
