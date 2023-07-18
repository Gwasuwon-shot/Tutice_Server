package gwasuwonshot.tutice.lesson.exception.notfound;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class NotFoundLessonException extends BasicException {
    public NotFoundLessonException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }



}
