package rs.raf.messageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.messageservice.domain.MessageType;

@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, String> {
}
