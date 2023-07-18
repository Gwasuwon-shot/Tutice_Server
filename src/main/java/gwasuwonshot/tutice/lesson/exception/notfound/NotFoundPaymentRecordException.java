package gwasuwonshot.tutice.lesson.exception.notfound;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class NotFoundPaymentRecordException extends BasicException {
    public NotFoundPaymentRecordException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }



}
