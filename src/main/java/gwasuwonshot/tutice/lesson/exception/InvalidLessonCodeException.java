package gwasuwonshot.tutice.lesson.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class InvalidLessonCodeException extends BasicException {
    public InvalidLessonCodeException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }


}
