package ptc2025.backend.Controller.facultyCorrelatives;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Universities.UniversityDTO;
import ptc2025.backend.Models.DTO.facultyCorrelatives.facultyCorrelativesDTO;
import ptc2025.backend.Services.facultyCorrelatives.facultyCorrelativesService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/FacultyCorrelative")
public class facultyCorrelativesController {
    @Autowired
    private facultyCorrelativesService services;

    @GetMapping("/getFacultyCorrelatives")
    public List<facultyCorrelativesDTO> getCorrelativo() {
        return services.getFacultyCorrelatives();
    }

    @PostMapping("/newFacultyCorrelatives")
    public ResponseEntity<Map<String, Object>> registrarCorrelativo(
            @Valid @RequestBody facultyCorrelativesDTO dto,
            HttpServletRequest request){
        try {
            facultyCorrelativesDTO respuesta = services.insertFacultyCorrelatives(dto);
            if (respuesta ==null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos del correlativo"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar el correlativo",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/updateFacultyCorrelatives/{id}")
    public ResponseEntity<?> modificarUniversidad(
            @PathVariable String id,
            @Valid @RequestBody facultyCorrelativesDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            facultyCorrelativesDTO actualizado = services.updateFacultyCorrelatives(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Correlativo no encontrado",
                    "detail", e.getMessage()
            ));
        }

    }
    @DeleteMapping("/deleteFacultyCorrelatives/{id}")
    public ResponseEntity <Map<String, Object>> eliminarCorrelativo(@PathVariable String id){
        try {
            if(!services.deleteFacultyCorrelatives(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Correlativo no encontrado")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "El correlativo no fue encontrado",
                                "timestamp", Instant.now().toString() //Marca el tiempo del error
                        ));
            }
            // Si la eliminación fue exitosa, retorna 200 (OK) con mensaje de confirmación
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Correlativo eliminado exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar correlativo",
                    "detail", e.getMessage()
            ));
        }
    }
}
