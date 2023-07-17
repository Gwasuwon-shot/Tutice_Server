package gwasuwonshot.tutice.schedule.repository;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.entity.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByDateBetweenAndLessonInOrderByDate(LocalDate startDate, LocalDate endDate, List<Lesson> lessonList);
    List<Schedule> findAllByDateAndLessonIn(LocalDate now, List<Lesson> lessonList);

    List<Schedule> findAllByDateAndLessonInOrderByStartTime(LocalDate now, List<Lesson> lessonList);

    Integer countByLesson_IdxAndStatusIn(Long lessonIdx, List<ScheduleStatus> attendanceStatusList);

    List<Schedule> findAllByDateAndStatusAndLessonInOrderByStartTimeDesc(LocalDate now, ScheduleStatus scheduleStatus, List<Lesson> lessonList);

    Schedule findTopByLessonOrderByDateDesc(Lesson lesson);

    Schedule findTopByLessonAndStatusNotOrderByDateDesc(Lesson lesson,ScheduleStatus status);


}
