package gwasuwonshot.tutice.user.repository;

import gwasuwonshot.tutice.user.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
}
