package ptc2025.backend.Services.EvaluationPlans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.EvaluationPlans.EvaluationPlansEntity;
import ptc2025.backend.Models.DTO.EvaluationPlans.EvaluationPlansDTO;
import ptc2025.backend.Respositories.EvaluationPlans.EvaluationPlansRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvaluationPlansService {
    @Autowired
    EvaluationPlansRepository repo;

    //Get
    public List<EvaluationPlansDTO> getEvaluationPlans(){
        List<EvaluationPlansEntity> plan = repo.findAll();
        return plan.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());

    }

    //POST
    public EvaluationPlansDTO insertEvaluationPlan(EvaluationPlansDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Plan de evaluación no puede ser nulo o vacio");
        }
        try{
            EvaluationPlansEntity entity = convertirAEntity(dto);
            EvaluationPlansEntity save =    repo.save(entity);
            return convertirADTO(save);
        }
        catch(Exception e){
            throw new RuntimeException("No se puede crear plan de evaluacion " + e.getMessage() );
        }
    }

    //PUT
    public EvaluationPlansDTO updateEvaluationPlan(String id, EvaluationPlansDTO dto){
        try{
            if(repo.existsById(id)){
                EvaluationPlansEntity entity = repo.getById(id);

                entity.setCourseOfferingID(dto.getCourseOfferingID());
                entity.setPlanName(dto.getPlanName());
                entity.setDescription(dto.getDescription());
                entity.setCreatedAt(dto.getCreatedAt());

                EvaluationPlansEntity save = repo.save(entity);
                return convertirADTO(save);
            }
            throw new IllegalArgumentException("El plan de evaluacion con el id" + id + " no pudo ser actualizado");
        }
        catch (Exception e){
            throw new RuntimeException("No se pudop actualizar el plan de evaluacion " + e.getMessage() );
        }
    }

    //DELETE

    public boolean deleteEvaluationPlan(String id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }
            else{
                return false;
            }
        }
        catch(EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("El plan de evaluacion con el id " + id + " no existe", 1);
        }
    }

    //Convertir a DTO
    private EvaluationPlansDTO convertirADTO(EvaluationPlansEntity entity){
        EvaluationPlansDTO dto = new EvaluationPlansDTO();
        dto.setEvaluationPlanID(entity.getEvaluationPlanID());
        dto.setCourseOfferingID(entity.getCourseOfferingID());
        dto.setPlanName(entity.getPlanName());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private EvaluationPlansEntity convertirAEntity(EvaluationPlansDTO dto){
        EvaluationPlansEntity entity = new EvaluationPlansEntity();
        entity.setEvaluationPlanID(dto.getEvaluationPlanID());
        entity.setCourseOfferingID(dto.getCourseOfferingID());
        entity.setPlanName(dto.getPlanName());
        entity.setDescription(dto.getDescription());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

}

/**EVALUATIONPLANID
 COURSEOFFERINGID
 PLANNAME
 DESCRIPTION
 CREATEDAT*/

/**evaluationPlanID
 courseOfferingID
 planName
 description
 createdAt*/
//EvaluationPlans