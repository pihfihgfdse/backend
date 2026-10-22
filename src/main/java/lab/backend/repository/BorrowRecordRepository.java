package lab.backend.repository;

import lab.backend.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findByUserIdAndDeviceIdAndStatus(Long userId, Long deviceId, String status);
}