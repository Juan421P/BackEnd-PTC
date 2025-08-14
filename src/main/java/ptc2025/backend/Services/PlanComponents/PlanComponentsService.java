package ptc2025.backend.Services.PlanComponents;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ptc2025.backend.Entities.PlanComponents.PlanComponentsEntity;
import ptc2025.backend.Models.DTO.PlanComponents.PlanComponentsDTO;
import ptc2025.backend.Respositories.PlanComponents.PlanComponentsRespository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlanComponentsService {
    @Autowired
    PlanComponentsRespository repo;
    //Get
    public List<PlanComponentsDTO> getPlanComponent (){
        List<PlanComponentsEntity> components = repo.findAll();
        return components.stream()
                .map(this:: convertirADTO)
                .collect(Collectors.toList());
    }
    //post
    public PlanComponentsDTO insertPlanComponents (@RequestBody PlanComponentsDTO dto){
        //Validacion
        if (dto == null ||
                dto.getInstrumentID() == null || dto.getInstrumentID().isBlank() ||
                dto.getEvaluationPlanID() == null || dto.getEvaluationPlanID().isBlank() ||
                dto.getComponentName() == null || dto.getComponentName().isBlank() ||
                dto.getWeightPercentage() == null ||
                dto.getWeightPercentage() <= 0 || dto.getWeightPercentage() > 100 ||
                (dto.getOrderIndex() != null && dto.getOrderIndex() < 1)){ //que no venga algun gracioso a poner -10
            throw new IllegalArgumentException("Todos los campos obligatorios deben estar completos y válidos");
        }
        try {
            PlanComponentsEntity entity = convertirAEntity(dto);
            PlanComponentsEntity guardardo = repo.save(entity);

            return convertirADTO (guardardo);
        }catch (Exception e){
            log.error("Error al guardar el componente de evaluación: " + e.getMessage());
            throw new RuntimeException("Error interno al registrar el componente");
        }
    }
    //Put
    public PlanComponentsDTO updatePlanComponents (String id, PlanComponentsDTO dto){
        PlanComponentsEntity existente = new PlanComponentsEntity();
        existente.setInstrumentID(dto.getInstrumentID());
        existente.setEvaluationPlanID(dto.getEvaluationPlanID());
        existente.setRubric(dto.getRubric());
        existente.setComponentName(dto.getComponentName());
        existente.setWeightPercentage(dto.getWeightPercentage());
        existente.setOrderIndex(dto.getOrderIndex());
        PlanComponentsEntity actualizar = repo.save(existente);
        return convertirADTO(actualizar);
    }
    //delete
    public boolean deletePlanComponents(String id){
        try {
            PlanComponentsEntity objCompo = repo.findById(id).orElse(null);
            if (objCompo != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Componente de evaluación no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException ("No se encontro ningún componente de evaluación con el ID: " + id + "para poder ser eliminado", 1);
        }

    }
    //Convertir a DTO
    private PlanComponentsDTO convertirADTO (PlanComponentsEntity entity){
        PlanComponentsDTO dto = new PlanComponentsDTO();
        dto.setComponentID(entity.getComponentID());
        dto.setInstrumentID(entity.getInstrumentID());
        dto.setEvaluationPlanID(entity.getEvaluationPlanID());
        dto.setRubric(entity.getRubric());
        dto.setComponentName(entity.getComponentName());
        dto.setWeightPercentage(entity.getWeightPercentage());
        dto.setOrderIndex(entity.getOrderIndex() != null ? entity.getOrderIndex() : 1);
        return dto;
    }
    //Convertir a Entity
    public PlanComponentsEntity convertirAEntity(PlanComponentsDTO dto) {
        PlanComponentsEntity entity = new PlanComponentsEntity();

        entity.setInstrumentID(dto.getInstrumentID());
        entity.setEvaluationPlanID(dto.getEvaluationPlanID());
        entity.setRubric(dto.getRubric());
        entity.setComponentName(dto.getComponentName().trim());
        entity.setWeightPercentage(dto.getWeightPercentage());

        // Si no se envía orderIndex, se usa 1 por defecto
        entity.setOrderIndex(dto.getOrderIndex() != null ? dto.getOrderIndex() : 1);

        return entity;
    }
}
