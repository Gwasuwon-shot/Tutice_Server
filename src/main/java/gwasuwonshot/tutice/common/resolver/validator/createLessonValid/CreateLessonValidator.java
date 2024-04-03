package gwasuwonshot.tutice.common.resolver.validator.createLessonValid;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.dto.request.createLesson.CreateLessonRequestRegularSchedule;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

@Component
public class CreateLessonValidator implements ConstraintValidator<CreateLessonValid, CreateLessonRequestRegularSchedule> {
    @Override
    public boolean isValid(CreateLessonRequestRegularSchedule value, ConstraintValidatorContext context) {
        // 미션 시작, 종료 시간만 확인 (현재시간 < 시작시간 < 종료시간)
        LocalTime startTime = DateAndTimeConvert.stringConvertLocalTime(value.getStartTime());
        LocalTime endTime = DateAndTimeConvert.stringConvertLocalTime(value.getEndTime());
        return startTime.isBefore(endTime);
    }

    public boolean isValid(String startTimeString, String endTimeString){
        // 미션 시작, 종료 시간만 확인 (현재시간 < 시작시간 < 종료시간)
        LocalTime startTime = DateAndTimeConvert.stringConvertLocalTime(startTimeString);
        LocalTime endTime = DateAndTimeConvert.stringConvertLocalTime(endTimeString);
        return startTime.isBefore(endTime);
    }
}
