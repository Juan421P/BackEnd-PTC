package ptc2025.backend.Respositories.DataAuditLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptc2025.backend.Entities.DataAuditLog.DataAuditLogEntity;
@Repository
public interface DataAuditLogRespository extends JpaRepository<DataAuditLogEntity, String> {
}
