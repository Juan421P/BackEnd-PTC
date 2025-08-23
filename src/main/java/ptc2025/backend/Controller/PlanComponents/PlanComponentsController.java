package ptc2025.backend.Controller.PlanComponents;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.PlanComponents.PlanComponentsDTO;
import ptc2025.backend.Services.PlanComponents.PlanComponentsService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/EvaluationPlanComponents")
public class PlanComponentsController {
    @Autowired
    PlanComponentsService service;
    @GetMapping("/getEvaluationPlanComponents")
    public List<PlanComponentsDTO> getEvaluationPlanComponents(){
        return  service.getPlanComponent();
    }
    @PostMapping("/newEvaluationPlanComponents")
    public ResponseEntity<Map<String, Object>> newEvaluationPlanComponents(
            @Valid @RequestBody PlanComponentsDTO dto,
            HttpServletRequest request){
        try {
            PlanComponentsDTO respuesta = service.insertPlanComponents(dto);
            if(respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos del componente de evaluación invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar el componente de evaluación",
                    "detail", e.getMessage()
            ));
        }

    }
    @PutMapping("/uploadEvaluationPlanComponents/{id}")
    public ResponseEntity<?> uploadEvaluationPlanComponents (
            @PathVariable String id,
            @Valid @RequestBody PlanComponentsDTO dto,
            BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            PlanComponentsDTO actualizado =service.updatePlanComponents(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Componente de evaluación no encontrad",
                    "detail", e.getMessage()
            ));
        }

    }
    @DeleteMapping("/deleteEvaluationPlanComponents/{id}")
    public ResponseEntity<Map<String, Object>> deleteEvaluationPlanComponents( @PathVariable String id){
        try {
            if (!service.deletePlanComponents(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Tipo de componente de evaluación no encontado")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "El componente de evaluación no fue encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Componente de evaluación eliminado exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar componente de evaluación",
                    "detail", e.getMessage()
            ));
        }
    }
}
//EvaluationPlanComponents
//componente de evaluación