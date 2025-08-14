package ptc2025.backend.Respositories.EntityType;

import org.springframework.data.jpa.repository.JpaRepository;
import ptc2025.backend.Entities.EntityType.EntityTypesEntity;

public interface EntityTypesRepository extends JpaRepository <EntityTypesEntity, String> {
}
