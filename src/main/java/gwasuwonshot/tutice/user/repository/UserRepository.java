package gwasuwonshot.tutice.user.repository;

import gwasuwonshot.tutice.user.entity.Provider;
import gwasuwonshot.tutice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndProvider(String email, Provider provider);

}
