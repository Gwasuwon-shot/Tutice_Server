package gwasuwonshot.tutice.lesson.repository;

import gwasuwonshot.tutice.lesson.entity.Lesson;
import gwasuwonshot.tutice.lesson.entity.RegularSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegularScheduleRepository extends JpaRepository<RegularSchedule, Long> {
}
