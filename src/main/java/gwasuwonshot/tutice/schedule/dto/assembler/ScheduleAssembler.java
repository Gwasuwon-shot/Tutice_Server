package gwasuwonshot.tutice.schedule.dto.assembler;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.entity.ScheduleStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ScheduleAssembler {
    public Schedule toEntity(Lesson lesson, LocalDate date, Long cycle,
                             LocalTime startTime, LocalTime endTime){
        return Schedule.builder()
                .lesson(lesson)
                .date(date)
                .cycle(cycle)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
