package ptc2025.backend.Respositories.careers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptc2025.backend.Entities.careers.CareerEntity;

@Repository
public interface CareerRepository extends JpaRepository<CareerEntity, String> {
}
