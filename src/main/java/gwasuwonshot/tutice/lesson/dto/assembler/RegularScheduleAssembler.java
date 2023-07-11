package gwasuwonshot.tutice.lesson.dto.assembler;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.RegularSchedule;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.DayOfWeek;

@Component
public class RegularScheduleAssembler {

    public RegularSchedule toEntity(Lesson lesson, Time startTime, Time endTime, DayOfWeek dayOfWeek){
        return RegularSchedule.builder()
                .lesson(lesson)
                .startTime(startTime)
                .endTime(endTime)
                .dayOfWeek(dayOfWeek)
                .build();
    }
}
