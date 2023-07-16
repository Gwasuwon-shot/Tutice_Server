package gwasuwonshot.tutice.lesson.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class AlreadyFinishedLessonException extends BasicException {
    public AlreadyFinishedLessonException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
