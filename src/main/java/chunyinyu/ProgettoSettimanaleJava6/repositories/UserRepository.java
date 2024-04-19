package chunyinyu.ProgettoSettimanaleJava6.repositories;

import chunyinyu.ProgettoSettimanaleJava6.entities.Event;
import chunyinyu.ProgettoSettimanaleJava6.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
//    Page<User> findAllByEvent(Event event, Pageable pageable);

}
