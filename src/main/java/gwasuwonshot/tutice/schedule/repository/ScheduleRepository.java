package gwasuwonshot.tutice.schedule.repository;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.schedule.entity.Schedule;
import gwasuwonshot.tutice.schedule.entity.ScheduleStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
    Schedule findTop1ByLessonAndCycleAndStatusNotOrderByDateDesc(Lesson lesson, Long cycle, ScheduleStatus scheduleStatus);
    List<Schedule> findAllByLessonAndCycleAndStatusNot(Lesson lesson, Long cycle, ScheduleStatus scheduleStatus, Sort sort);

    List<Schedule> findAllByLessonInAndDateGreaterThanEqualOrderByDate(List<Lesson> lessonList, LocalDate now);

    boolean existsByLessonInAndStatusNot(List<Lesson> lessonList, ScheduleStatus scheduleStatus);

    boolean existsByStatusAndDateIsBeforeAndLessonIn(ScheduleStatus scheduleStatus, LocalDate now, List<Lesson> lessonList);

    boolean existsByStatusAndDateAndStartTimeLessThanEqualAndLessonInOrderByDate(ScheduleStatus scheduleStatus, LocalDate now, LocalTime now1, List<Lesson> lessonList);
}
