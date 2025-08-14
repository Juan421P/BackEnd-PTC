package ptc2025.backend.Respositories.PlanComponents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptc2025.backend.Entities.PlanComponents.PlanComponentsEntity;

@Repository
public interface PlanComponentsRespository extends JpaRepository<PlanComponentsEntity, String> {
}
