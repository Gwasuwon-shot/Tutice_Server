package gwasuwonshot.tutice.lesson.exception;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class AlreadyExistLessonParentsException extends BasicException {
    public AlreadyExistLessonParentsException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
