package ptc2025.backend.Controller.RequirementConditions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.RequirementConditions.RequirementConditionsDTO;
import ptc2025.backend.Services.RequirementConditions.RequirementConditionsService;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/RequirementConditions")
public class RequirementConditionsController {

    @Autowired
    RequirementConditionsService service;

    @PostMapping("/newRequirementCondition")
    public ResponseEntity<?> newRequirementConditions(@Valid @RequestBody RequirementConditionsDTO json, HttpServletRequest request){
        try{
            RequirementConditionsDTO response = service.insertRequirementCondition(json);
            if(response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Los datos no pudieron ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "Success",
                    "data", response
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message", "Error no controlado al registrar el requerimiento",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/updateRequirementCondition/{id}")
    public ResponseEntity<?> updateRequirementCondition(@PathVariable String id, @Valid @RequestBody RequirementConditionsDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            RequirementConditionsDTO dto = service.updateRequirementCondition(id, json);
            return ResponseEntity.ok(dto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "Error", "Datos duplicados",
                    "Campo", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/deleteRequirementCondition/{id}")
    public ResponseEntity<?> deleteRequirementCondition(@PathVariable String id){
        try{
            if(!service.deleteRequirementCondition(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Mensaje Error", "Requisito no encontrado").body(Map.of(
                        "Error", "Not found",
                        "Mensaje", "El requisito no ha sido encontrado",
                        "timestmap", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Requisito eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el requisito",
                    "detail", e.getMessage()
            ));
        }
    }
}
