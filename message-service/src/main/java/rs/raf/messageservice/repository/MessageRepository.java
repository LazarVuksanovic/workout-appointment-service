package rs.raf.messageservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.messageservice.domain.Message;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Message a SET a.messageType = null WHERE a.messageType.messageType = :messageType")
    void updateGymTrainingTypeToNull(String messageType);
    Optional<Page<Message>> findByUserId(Pageable pageable, Long userId);
}
