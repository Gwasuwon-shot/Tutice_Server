package gwasuwonshot.tutice.lesson.repository;

import gwasuwonshot.tutice.lesson.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
}
