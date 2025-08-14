package ptc2025.backend.Controller.Universities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Universities.UniversityDTO;
import ptc2025.backend.Services.Universities.UniversityServices;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/apiUniversity") //los endPoint se anteceden de la palabra api
public class UniversityController {
    @Autowired
    private UniversityServices services;

    //Get
    @GetMapping("/getDataUniversity")
    public List<UniversityDTO> getUniversity() {
        return services.getUniversityService();
    }
    //Post
    @PostMapping("/newUniversity")
    public ResponseEntity<Map<String, Object>> registrarUniversidad(
            @Valid @RequestBody UniversityDTO dtoUni,
            HttpServletRequest request){
        try {
            UniversityDTO respuesta = services.insertarUniversidad(dtoUni);
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
    //Put
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarUniversidad(
            @PathVariable String id,
            @Valid @RequestBody UniversityDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            UniversityDTO actualizado = services.modificarUniversidad(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Universidad no encontrada",
                    "detail", e.getMessage()
            ));
        }

    }
    //Delete
    @DeleteMapping("/eliminarUniversidad/{id}")
    public ResponseEntity <Map<String, Object>> eliminarUniversidad(@PathVariable String id){
        try {
            if(!services.elminarUniversidad(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Universidad no encontrada")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "La universidad no fue encontrada",
                                "timestamp", Instant.now().toString() //Marca el tiempo del error
                        ));
            }
            // Si la eliminación fue exitosa, retorna 200 (OK) con mensaje de confirmación
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Universidad eliminada exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar universidad",
                    "detail", e.getMessage()
            ));
        }
    }





}

