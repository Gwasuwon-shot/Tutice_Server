package gwasuwonshot.tutice.lesson.dto.assembler;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.RegularSchedule;
import gwasuwonshot.tutice.lesson.entity.DayOfWeek;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

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
}
