package gwasuwonshot.tutice.schedule.dto.assembler;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.entity.ScheduleStatus;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;

@Component
public class ScheduleAssembler {
    public Schedule toEntity(Lesson lesson, Date date, ScheduleStatus status,
                             Long cycle, Time startTime, Time endTime){
        return Schedule.builder()
                .lesson(lesson)
                .date(date)
                .status(status)
                .cycle(cycle)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
