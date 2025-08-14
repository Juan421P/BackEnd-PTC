package ptc2025.backend.Services.SocialService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.SocialService.SocialServiceEntity;
import ptc2025.backend.Models.DTO.SocialService.SocialServiceDTO;
import ptc2025.backend.Respositories.SocialService.SocialServiceRespository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SocialServiceServices {
    @Autowired
    SocialServiceRespository repo;
    public List<SocialServiceDTO> getSocialService(){
        List<SocialServiceEntity> servicioSocial = repo.findAll();
        return servicioSocial.stream()
                .map(this::convertirSSDTO)
                .collect(Collectors.toList());
    }
    public SocialServiceDTO insertarServicioSocial(SocialServiceDTO dto){
        if(dto.getUniversityID() == null || dto.getUniversityID().isBlank() ||
                dto.getSocialServiceProjectName() == null || dto.getSocialServiceProjectName().isBlank() ||
                dto.getDescription() ==null || dto.getDescription().isBlank()){
            throw new IllegalArgumentException("Todos los campos deben de estar completos");
        }
        try {
            SocialServiceEntity entidad = convertirSSEntity(dto);
            SocialServiceEntity guardar = repo.save(entidad);
            return convertirSSDTO(guardar);
        }catch (Exception e){
            log.error("Error al registrar el servicio social del estudiante" +  e.getMessage());
            throw new RuntimeException("Error al intentar guardar el servicio social");
        }
    }
    public SocialServiceDTO modificarServicioSocial(String id, SocialServiceDTO dto){
        SocialServiceEntity servicioExistente = repo.findById(id).orElseThrow(() -> new RuntimeException("El dato no pudo ser actualizado. Localidad no encontrada"));
        servicioExistente.setUniversityID(dto.getUniversityID());
        servicioExistente.setSocialServiceProjectName(dto.getSocialServiceProjectName());
        servicioExistente.setDescription(dto.getDescription());
        SocialServiceEntity actualizado = repo.save(servicioExistente);
        return convertirSSDTO(actualizado);
    }
    public boolean eliminarServicioSocial(String id){
        try{
            SocialServiceEntity objServicio = repo.findById(id).orElse(null);
            if (objServicio != null){
                repo.deleteById(id);
                return true;
            }else {
                System.out.println("Servicio social no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro ninguna servicio social con el ID" + id , 1);
        }
    }
    private SocialServiceDTO convertirSSDTO(SocialServiceEntity entity){
        SocialServiceDTO dto = new SocialServiceDTO();
        dto.setSocialServiceProjectID(entity.getSocialServiceProjectID());
        dto.setUniversityID(entity.getUniversityID());
        dto.setSocialServiceProjectName(entity.getSocialServiceProjectName());
        dto.setDescription(entity.getDescription());
        return dto;

    }
    private SocialServiceEntity convertirSSEntity (SocialServiceDTO dto){
        SocialServiceEntity entity = new SocialServiceEntity();
        entity.setUniversityID(dto.getUniversityID());
        entity.setSocialServiceProjectName(dto.getSocialServiceProjectName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
