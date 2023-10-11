package gwasuwonshot.tutice.common.resolver.validator.createLessonValid;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequestRegularSchedule;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class CreateLessonValidator implements ConstraintValidator<CreateLessonValid, CreateLessonRequestRegularSchedule> {
    @Override
    public boolean isValid(CreateLessonRequestRegularSchedule value, ConstraintValidatorContext context) {
        // 미션 시작, 종료 시간만 확인 (현재시간 < 시작시간 < 종료시간)
        LocalTime startTime = DateAndTimeConvert.stringConvertLocalTime(value.getStartTime());
        LocalTime endTime = DateAndTimeConvert.stringConvertLocalTime(value.getEndTime());
        return startTime.isBefore(endTime);
    }
}
