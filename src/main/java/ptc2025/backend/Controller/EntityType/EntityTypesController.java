package ptc2025.backend.Controller.EntityType;

import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.EntityType.EntityTypesDTO;
import ptc2025.backend.Models.DTO.PlanComponents.PlanComponentsDTO;
import ptc2025.backend.Respositories.EntityType.EntityTypesRepository;
import ptc2025.backend.Services.EntityType.EntityTypesService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/apiEntityType")
public class EntityTypesController{

    @Autowired
    EntityTypesService service;
    @GetMapping("/getEntityType")
    public List<EntityTypesDTO> getEntityType(){
        return  service.getEntityTypes();
    }
    @PostMapping("/newEntityType")
    public ResponseEntity<Map<String, Object>> newEntityType(
            @Valid @RequestBody EntityTypesDTO dto,
            HttpServletRequest request){
        try {
            EntityTypesDTO respuesta = service.insertEntityType(dto);
            if(respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos del tipo de entidad invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar el tipo de entidad",
                    "detail", e.getMessage()
            ));
        }

    }
    @PutMapping("/updateEntityType/{id}")
    public ResponseEntity<?> updateEntityType (
            @PathVariable String id,
            @Valid @RequestBody EntityTypesDTO dto,
            BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            EntityTypesDTO actualizado =service.updateEntityType(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Tipo de entidad no encontrado",
                    "detail", e.getMessage()
            ));
        }

    }
    @DeleteMapping("/deleteEntityType/{id}")
    public ResponseEntity<Map<String, Object>> deleteEntityType( @PathVariable String id){
        try {
            if (!service.deleteEntityType(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Tipo de entidad no encontado")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "El tipo de entidad no fue encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Tipo de entidad eliminado exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar tipo de entidad",
                    "detail", e.getMessage()
            ));
        }
    }
}
