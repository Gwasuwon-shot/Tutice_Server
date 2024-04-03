package gwasuwonshot.tutice.common.resolver.validator.createLessonValid;


import gwasuwonshot.tutice.common.exception.ErrorStatus;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
@Documented
@Target({ TYPE , ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {CreateLessonValidator.class})

public @interface CreateLessonValid {
    String message() default ErrorStatus.INVALID_REGULAR_SCHEDULE_TIME_MESSAGE;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
