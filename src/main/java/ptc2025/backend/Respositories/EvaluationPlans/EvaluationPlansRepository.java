package ptc2025.backend.Respositories.EvaluationPlans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptc2025.backend.Entities.EvaluationPlans.EvaluationPlansEntity;

@Repository
public interface EvaluationPlansRepository extends JpaRepository<EvaluationPlansEntity, String> {
}
