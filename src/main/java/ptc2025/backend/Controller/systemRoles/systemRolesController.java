package ptc2025.backend.Controller.systemRoles;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Universities.UniversityDTO;
import ptc2025.backend.Models.DTO.systemRoles.systemRolesDTO;
import ptc2025.backend.Services.systemRoles.systemRolesService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/SystemRol")
public class systemRolesController {
    @Autowired
    private systemRolesService services;

    @GetMapping("/getSystemRol")
    public List<systemRolesDTO> getUniversity() {
        return services.getSystemRoles();
    }

    @PostMapping("/newSystemaRol")
    public ResponseEntity<Map<String, Object>> registrarSystemRole(
            @Valid @RequestBody systemRolesDTO dto,
            HttpServletRequest request){
        try {
            systemRolesDTO respuesta = services.insertSystemRoles(dto);
            if (respuesta ==null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos de rol del sistema invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar el rol en el sistema",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/updateSystemRol/{id}")
    public ResponseEntity<?> modificarSystemRol(
            @PathVariable String id,
            @Valid @RequestBody systemRolesDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            systemRolesDTO actualizado = services.updateSystemRoles(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Rol no encontrado",
                    "detail", e.getMessage()
            ));
        }

    }
    @DeleteMapping("/eliminarSystemRol/{id}")
    public ResponseEntity <Map<String, Object>> eliminarRol(@PathVariable String id){
        try {
            if(!services.deleteSystemRoles(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Rol del sistema no encontrado")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "El rol no fue encontrado",
                                "timestamp", Instant.now().toString() //Marca el tiempo del error
                        ));
            }
            // Si la eliminación fue exitosa, retorna 200 (OK) con mensaje de confirmación
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Rol eliminada exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar rol del sistema",
                    "detail", e.getMessage()
            ));
        }
    }
}
