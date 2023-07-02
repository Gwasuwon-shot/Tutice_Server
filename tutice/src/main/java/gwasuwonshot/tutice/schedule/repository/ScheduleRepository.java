package gwasuwonshot.tutice.schedule.repository;

import gwasuwonshot.tutice.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
