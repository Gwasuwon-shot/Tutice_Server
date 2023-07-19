package gwasuwonshot.tutice.lesson.exception.notfound;

import gwasuwonshot.tutice.common.exception.BasicException;
import gwasuwonshot.tutice.common.exception.ErrorStatus;

public class NotFoundLessonParentsException extends BasicException {
    public NotFoundLessonParentsException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }

}
