package ptc2025.backend.Services.Localities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.Localities.LocalitiesEntity;
import ptc2025.backend.Models.DTO.Localities.LocalitiesDTO;
import ptc2025.backend.Respositories.Localities.LocalitiesRespository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocalitiesService {
    @Autowired
    LocalitiesRespository repo;
    public List<LocalitiesDTO> getLocalitiesService(){
        List<LocalitiesEntity> localidad = repo.findAll();
        return localidad.stream()
                .map(this::convertirALocaltityDTO)
                .collect(Collectors.toList());
    }

    public LocalitiesDTO insertarLocalidad(LocalitiesDTO dto){
        if (dto.getUniversityID() == null || dto.getUniversityID().isBlank() ||
                dto.getIsMainLocality() == null ||
                dto.getAddress() == null || dto.getAddress().isBlank() ||
                dto.getPhoneNumber() == null || dto.getPhoneNumber().isBlank()) {

            throw new IllegalArgumentException("Todos los campos obligatorios deben estar completos (universityID, isMainLocality, address, phoneNumber).");
        }
        try{
            LocalitiesEntity entidad = convertirALocaltityEntity(dto);
            LocalitiesEntity guardar = repo.save(entidad);
            return convertirALocaltityDTO(guardar);
        }catch (Exception e){
            log.error("Error al registrar una nueva localidad" +  e.getMessage());
            throw new RuntimeException("Error al intentar guardar la localidad");
        }
    }

    public LocalitiesDTO modificarLocalidad(String id, LocalitiesDTO dto){
        LocalitiesEntity localidadExistente = repo.findById(id).orElseThrow(() -> new RuntimeException("El dato no pudo ser actualizado. Localidad no encontrada"));
        localidadExistente.setUniversityID(dto.getUniversityID());
        localidadExistente.setIsMainLocality(dto.getIsMainLocality());
        localidadExistente.setAddress(dto.getAddress());
        localidadExistente.setPhoneNumber(dto.getPhoneNumber());
        LocalitiesEntity actualizado = repo.save(localidadExistente);
        return convertirALocaltityDTO(actualizado);
    }

    public boolean eliminarLocalidad (String id){
        try {
            LocalitiesEntity objLocalidad = repo.findById(id).orElse(null);
            if (objLocalidad != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Localidad no encontrada");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro ninguna localidad con el ID" + id , 1);
        }
    }

    private LocalitiesDTO convertirALocaltityDTO(LocalitiesEntity localities) {
        LocalitiesDTO dto = new LocalitiesDTO();
        dto.setLocalityID(localities.getLocalityID());
        dto.setUniversityID(localities.getUniversityID());
        dto.setIsMainLocality(localities.getIsMainLocality());
        dto.setAddress(localities.getAddress());
        dto.setPhoneNumber(localities.getPhoneNumber());
        return dto;
    }

    private LocalitiesEntity convertirALocaltityEntity(LocalitiesDTO dto){
        LocalitiesEntity entity = new LocalitiesEntity();
        entity.setUniversityID(dto.getUniversityID());
        entity.setIsMainLocality(dto.getIsMainLocality());
        entity.setAddress(dto.getAddress());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }
}
