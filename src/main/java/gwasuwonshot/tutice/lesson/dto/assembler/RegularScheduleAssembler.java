package gwasuwonshot.tutice.lesson.dto.assembler;

import gwasuwonshot.tutice.common.module.DateAndTimeConvert;
import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.RegularSchedule;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static gwasuwonshot.tutice.lesson.entity.DayOfWeek.getDayOfWeekByValue;

@Component
public class RegularScheduleAssembler {

    public RegularSchedule toEntity(Lesson lesson, LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek){
        return RegularSchedule.builder()
                .lesson(lesson)
                .startTime(startTime)
                .endTime(endTime)
                .dayOfWeek(dayOfWeek)
                .build();
    }

    public RegularSchedule toTemporaryEntity(String dayOfWeek, String startTime, String endTime) {
        return RegularSchedule.builder()
                .dayOfWeek(getDayOfWeekByValue(dayOfWeek))
                .startTime(DateAndTimeConvert.stringConvertLocalTime(startTime))
                .endTime(DateAndTimeConvert.stringConvertLocalTime(endTime))
                .build();
    }
}
