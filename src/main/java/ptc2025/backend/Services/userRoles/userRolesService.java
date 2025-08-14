package ptc2025.backend.Services.userRoles;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.Notification.NotificationEntity;
import ptc2025.backend.Entities.Universities.UniversityEntity;
import ptc2025.backend.Entities.userRoles.userRolesEntity;
import ptc2025.backend.Models.DTO.Notification.NotificationDTO;
import ptc2025.backend.Models.DTO.Universities.UniversityDTO;
import ptc2025.backend.Models.DTO.userRoles.userRolesDTO;
import ptc2025.backend.Respositories.userRoles.userRolesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class userRolesService {
    @Autowired
    userRolesRepository repo;

    public List<userRolesDTO> getUserRole(){
        List<userRolesEntity> universidad = repo.findAll();
        return universidad.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public userRolesDTO insertUserRole (userRolesDTO dto){
        if (dto.getUserid() == null || dto.getUserid().isBlank() || dto.getRoleType() == null || dto.getRoleType().isBlank()) {
            throw new IllegalArgumentException("Todos los campos obligatorios deben estar completos: nombre del usuario y tipo de rol");
        }
        try {
            // Convertir DTO → Entity
            userRolesEntity entidad = convertirAEntity(dto);

            // Guardar en BD
            userRolesEntity guardado = repo.save(entidad);

            // Convertir Entity → DTO
            return convertirADTO(guardado);
        }catch (Exception e){
            log.error("Error al registrar el rol de usuario " + e.getMessage());
            throw new RuntimeException("Error interno al guardar el rol");
        }
    }
    //Put
    public userRolesDTO updateUserRole (String id, userRolesDTO dto){
        userRolesEntity existente = new userRolesEntity();
        existente.setUserid(dto.getUserid());
        existente.setRoleType(dto.getRoleType());
        userRolesEntity actualizar = repo.save(existente);
        return convertirADTO(actualizar);
    }
    //Delete
    public boolean deleteUserRole(String id){
        try {
            userRolesEntity objCompo = repo.findById(id).orElse(null);
            if (objCompo != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Rol de Usuario no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontraron ningun rol de usuario con el id: ", 1);

        }
    }

    public userRolesDTO convertirADTO (userRolesEntity entity){
        userRolesDTO dto = new userRolesDTO();
        dto.setUserRoleid(entity.getUserRoleid());
        dto.setUserid(entity.getUserid());
        dto.setRoleType(entity.getRoleType());
        return dto;
    }
    public userRolesEntity convertirAEntity (userRolesDTO dto){
        userRolesEntity entity = new userRolesEntity();
        entity.setUserid(dto.getUserid());
        entity.setUserRoleid(dto.getUserRoleid());
        entity.setRoleType(dto.getRoleType());
        return entity;
    }


}
