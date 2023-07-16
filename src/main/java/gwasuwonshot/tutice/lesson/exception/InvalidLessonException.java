package gwasuwonshot.tutice.lesson.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class InvalidLessonException extends BasicException {
    public InvalidLessonException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }


}