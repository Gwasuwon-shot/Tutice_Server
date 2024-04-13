package gwasuwonshot.tutice.user.repository;

import gwasuwonshot.tutice.user.entity.Provider;
import gwasuwonshot.tutice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndProvider(String email, Provider provider);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phone);

    Optional<User> findBySocialTokenAndProvider(String socialToken, Provider provider);

    boolean existsBySocialTokenAndProviderAndNameIsNotNull(String socialToken, Provider provider);

    boolean existsByPhoneNumberAndProviderNot(String phone, Provider provider);

    boolean existsByPhoneNumberAndProvider(String phone, Provider provider);

    User findByPhoneNumberAndProvider(String phone, Provider provider);
}
