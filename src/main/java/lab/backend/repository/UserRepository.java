package lab.backend.repository;

import lab.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名找用户（登录时要用）
    Optional<User> findByUsername(String username);
}
