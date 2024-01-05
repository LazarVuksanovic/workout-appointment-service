package rs.raf.messageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.messageservice.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
