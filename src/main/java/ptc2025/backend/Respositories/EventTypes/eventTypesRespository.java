package ptc2025.backend.Respositories.EventTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptc2025.backend.Entities.EventTypes.eventTypesEntity;

@Repository
public interface eventTypesRespository extends JpaRepository<eventTypesEntity, String> {
}
