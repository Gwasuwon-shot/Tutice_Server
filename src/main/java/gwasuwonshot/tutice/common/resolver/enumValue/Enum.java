package gwasuwonshot.tutice.common.resolver.enumValue;

import gwasuwonshot.tutice.common.exception.ErrorStatus;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {EnumValidator.class})
public @interface Enum {

    String message() default ErrorStatus.NOT_FOUND_ENUM_VALUE;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    //Class<? extends java.lang.Enum<?>> enumClass();
    Class<? extends EnumModel> enumClass();

    boolean ignoreCase() default false;
}
