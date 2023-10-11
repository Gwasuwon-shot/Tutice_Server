package gwasuwonshot.tutice.common.resolver.validator.createLessonValid;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
@Documented
@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {CreateLessonValidator.class})
public @interface CreateLessonValid {
    String message() default "수업 시작시간이 종료시간보다 늦습니다. ";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
