package ptc2025.backend.Services.EntityType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.EntityType.EntityTypesEntity;
import ptc2025.backend.Entities.PlanComponents.PlanComponentsEntity;
import ptc2025.backend.Models.DTO.EntityType.EntityTypesDTO;
import ptc2025.backend.Models.DTO.PlanComponents.PlanComponentsDTO;
import ptc2025.backend.Respositories.EntityType.EntityTypesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntityTypesService {
    @Autowired
    EntityTypesRepository repo;
    //Get
    public List<EntityTypesDTO> getEntityTypes (){
        List<EntityTypesEntity> components = repo.findAll();
        return components.stream()
                .map(this:: convertirADTO)
                .collect(Collectors.toList());
    }
    public EntityTypesDTO insertEntityType(EntityTypesDTO dto) {
        // Validaciones
        if (dto == null ||
                dto.getUniversityID() == null || dto.getUniversityID().isBlank() ||
                dto.getEntityType() == null || dto.getEntityType().isBlank()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios, no dejarlos vacios");
        }

        if (dto.getEntityType().length() > 60) {
            throw new IllegalArgumentException("El tipo de entidad no debe tener más de 60 caracteres");
        }


        if (dto.getIsAutoCodeEnabled() != null &&
                !(dto.getIsAutoCodeEnabled().equals("Y") || dto.getIsAutoCodeEnabled().equals("N"))) {
            throw new IllegalArgumentException("Solo puede ser 'Y' o 'N'");
        }


        try {
            EntityTypesEntity entity = convertirAEntity(dto);
            EntityTypesEntity guardado = repo.save(entity);
            return convertirADTO(guardado);
        } catch (Exception e) {
            log.error("Error al guardar el tipo de entidad: " + e.getMessage());
            throw new RuntimeException("Error interno al guardar el tipo de entidad");
        }
    }
    public EntityTypesDTO updateEntityType(String id, EntityTypesDTO dto){
        EntityTypesEntity existente = new EntityTypesEntity();
        existente.setUniversityID(dto.getUniversityID());
        existente.setEntityType(dto.getEntityType());
        existente.setIsAutoCodeEnabled(dto.getIsAutoCodeEnabled());
        EntityTypesEntity actualizar = repo.save(existente);
        return convertirADTO(actualizar);
    }
    public boolean deleteEntityType(String id){
        try {
            EntityTypesEntity objCompo = repo.findById(id).orElse(null);
            if (objCompo != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Tipo de entidad no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException ("No se encontro ningún tipo de entidad con el ID: " + id + "para poder ser eliminado", 1);
        }

    }
    private EntityTypesDTO convertirADTO (EntityTypesEntity entity){
        EntityTypesDTO dto = new EntityTypesDTO();
        dto.setEntityTypeID(entity.getEntityTypeID());
        dto.setUniversityID(entity.getUniversityID());
        dto.setEntityType(entity.getEntityType());
        dto.setIsAutoCodeEnabled(entity.getIsAutoCodeEnabled());
        return dto;
    }
    public EntityTypesEntity convertirAEntity(EntityTypesDTO dto) {
        EntityTypesEntity entity = new EntityTypesEntity();
        entity.setUniversityID(dto.getUniversityID());
        entity.setEntityType(dto.getEntityType());
        entity.setIsAutoCodeEnabled(dto.getIsAutoCodeEnabled());
        return entity;
    }
}
