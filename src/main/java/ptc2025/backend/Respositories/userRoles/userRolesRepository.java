package ptc2025.backend.Respositories.userRoles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptc2025.backend.Entities.userRoles.userRolesEntity;

@Repository
public interface userRolesRepository extends JpaRepository<userRolesEntity, String>{
}
