package rs.raf.messageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.messageservice.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Message a SET a.messageType = null WHERE a.messageType.messageType = :messageType")
    void updateGymTrainingTypeToNull(String messageType);
}
