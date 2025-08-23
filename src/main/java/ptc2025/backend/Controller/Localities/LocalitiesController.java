package ptc2025.backend.Controller.Localities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Localities.LocalitiesDTO;
import ptc2025.backend.Services.Localities.LocalitiesService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Locality")
public class LocalitiesController {
    @Autowired
    LocalitiesService service;

    @GetMapping("/getDataLocality")
    public List<LocalitiesDTO> getLocality(){
        return service.getLocalitiesService();
    }
    @PostMapping("/newLocality")
    public ResponseEntity<Map<String, Object>> registrarLocalidad(
            @Valid @RequestBody LocalitiesDTO dtoLocal,
            HttpServletRequest  request){
        try{
            LocalitiesDTO respuesta = service.insertarLocalidad(dtoLocal);
            if(respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos de localidad invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar la localidad",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/updateLocality/{id}")
    public ResponseEntity<?> modificarLocalidad(
            @PathVariable String id,
            @Valid @RequestBody LocalitiesDTO dto,
            BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            LocalitiesDTO actualizado = service.modificarLocalidad(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Localidad no encontrada",
                    "detail", e.getMessage()
            ));
        }
    }
    @DeleteMapping("/deleteLocation/{id}")
    public ResponseEntity<Map<String, Object>> eliminarLocalidad(@PathVariable String id){
        try{
            if(!service.eliminarLocalidad(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Localidad no encontrada")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "La localidad no fue encontrada",
                                "timestamp", Instant.now().toString()
                        ));

            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Localidad eliminada exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar localidad",
                    "detail", e.getMessage()
            ));
        }
    }
}
