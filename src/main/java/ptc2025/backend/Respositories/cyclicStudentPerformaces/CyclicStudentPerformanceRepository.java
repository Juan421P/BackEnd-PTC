package ptc2025.backend.Respositories.cyclicStudentPerformaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptc2025.backend.Entities.cyclicStudentPerformances.CyclicStudentPerformanceEntity;

@Repository
public interface CyclicStudentPerformanceRepository extends JpaRepository<CyclicStudentPerformanceEntity, String> { }

