package gwasuwonshot.tutice.common.resolver.enumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<Enum, String> {

    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<EnumValue>  enumValues = Arrays.stream(this.annotation.enumClass().getEnumConstants())
                .map(EnumValue::new)
                .collect(Collectors.toList());


        if (enumValues != null) {
            for (EnumValue enumValue : enumValues) {
                if (value.equals(enumValue.getValue())
                        || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.getValue()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
