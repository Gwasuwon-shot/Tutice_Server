package gwasuwonshot.tutice.user.repository;

import gwasuwonshot.tutice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
