package gwasuwonshot.tutice.schedule.repository;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByDateAndLessonIn(Date now, List<Lesson> lessonList);
    List<Schedule> findAllByDateBetweenAndLessonInOrderByDate(LocalDate startDate, LocalDate endDate, List<Lesson> lessonList);
}
