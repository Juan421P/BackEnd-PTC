package ptc2025.backend.Controller.SubjectDefinitions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.SubjectDefinitions.SubjectDefinitionsDTO;
import ptc2025.backend.Services.SubjectDefinitions.SubjectDefinitionsService;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/SubjectDefinitions")
public class SubjectDefinitionsController {

    @Autowired
    SubjectDefinitionsService service;

    @PostMapping("/newSubjectDefinition")
    public ResponseEntity<?> newDefinition(@Valid @RequestBody SubjectDefinitionsDTO json, HttpServletRequest request){
        try{
            SubjectDefinitionsDTO response = service.insertDefintion(json);
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
                    "message", "Error no controlado al registrar la definición",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/updateSubjectDefinition/{ID}")
    public ResponseEntity<?> updateDefinition(@PathVariable String id, @Valid @RequestBody SubjectDefinitionsDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            SubjectDefinitionsDTO dto = service.updateDefinition(id,json);
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

    @DeleteMapping("/deleteSubjectDefinition/{ID}")
    public ResponseEntity<?> deleteSubjectDefinition(@PathVariable String id){
        try{
            if (!service.deleteDefinition(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Mensaje Error", "Definición no encontrada").body(Map.of(
                        "Error", "Not Found",
                        "message", "La definición no ha sido encontrada",
                        "timsestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Usuario eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar la definición",
                    "detail", e.getMessage()
            ));
        }
    }
}
