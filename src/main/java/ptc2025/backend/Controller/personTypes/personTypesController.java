package ptc2025.backend.Controller.personTypes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Universities.UniversityDTO;
import ptc2025.backend.Models.DTO.personTypes.personTypesDTO;
import ptc2025.backend.Services.personTypes.personTypesService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/TypesPerson")
public class personTypesController {
    @Autowired
    private personTypesService services;

    @GetMapping("/getDataUniversity")
    public List<personTypesDTO> getUniversity() {
        return services.getPersonType();
    }

    @PostMapping("/newPersonType")
    public ResponseEntity<Map<String, Object>> registrarPersonType(
            @Valid @RequestBody personTypesDTO dto,
            HttpServletRequest request){
        try {
            personTypesDTO respuesta = services.insertPersonTypes(dto);
            if (respuesta ==null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos de universidad invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar la universidad",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/updatePersonType/{id}")
    public ResponseEntity<?> modificarPersonType(
            @PathVariable String id,
            @Valid @RequestBody personTypesDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            personTypesDTO actualizado = services.updatePersonTypes(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Tipo de persona no encontrada",
                    "detail", e.getMessage()
            ));
        }

    }
    @DeleteMapping("/deleteTypePerson/{id}")
    public ResponseEntity <Map<String, Object>> eliminarTypesPerson(@PathVariable String id){
        try {
            if(!services.deletePersonTypes(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Tipo de persona no encontrada")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "El tipo de persona no fue encontrada",
                                "timestamp", Instant.now().toString() //Marca el tiempo del error
                        ));
            }
            // Si la eliminación fue exitosa, retorna 200 (OK) con mensaje de confirmación
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Tipo de persona eliminado exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar",
                    "detail", e.getMessage()
            ));
        }
    }
}
