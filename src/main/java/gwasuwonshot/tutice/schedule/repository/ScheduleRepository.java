package gwasuwonshot.tutice.schedule.repository;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.entity.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByDateBetweenAndLessonInOrderByDate(LocalDate startDate, LocalDate endDate, List<Lesson> lessonList);
    List<Schedule> findAllByDateAndLessonIn(LocalDate now, List<Lesson> lessonList);
    List<Schedule> findAllByDateAndLessonInOrderByStartTime(LocalDate now, List<Lesson> lessonList);
    List<Schedule> findAllByDateAndStatusAndLessonInOrderByStartTimeDesc(LocalDate now, ScheduleStatus scheduleStatus, List<Lesson> lessonList);
    Schedule findTopByLessonOrderByDateDesc(Lesson lesson);
    List<Schedule> findAllByStatusAndDateAndStartTimeIsBeforeAndLessonIn(ScheduleStatus scheduleStatus, LocalDate now, LocalTime now1, List<Lesson> lessonList);

    Schedule findTopByLessonAndStatusNotOrderByDateDesc(Lesson lesson,ScheduleStatus status);
    Schedule findTopByLessonAndCycleAndStatusNotOrderByDateDesc(Lesson lesson, Long cycle, ScheduleStatus scheduleStatus);

    Schedule findTopByLessonAndCycleAndStatusNotOrderByDateAsc(Lesson lesson, Long cycle, ScheduleStatus scheduleStatus);

    Integer countByLesson_IdxAndStatusIn(Long lessonIdx, List<ScheduleStatus> attendanceStatusList);

    List<Schedule> findAllByStatusAndDateIsBeforeAndLessonInOrderByDate(ScheduleStatus scheduleStatus, LocalDate now, List<Lesson> lessonList);

    Long countByLessonAndCycleAndStatusIn(Lesson lesson, Long cycle, List<ScheduleStatus> statusList);

    List<Schedule> findAllByLessonAndCycleAndStatusIn(Lesson lesson, Long cycle, List<ScheduleStatus> statusList);


}
