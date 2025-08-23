package ptc2025.backend.Controller.EmployeeRoles;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.EmployeeRoles.EmployeeRolesDTO;
import ptc2025.backend.Services.EmployeeRoles.EmployeeRolesService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employeeRoles")
public class EmployeeRolesController {

    @Autowired
    private EmployeeRolesService services;

    @GetMapping("/getEmpleyeeRoles")
    public List<EmployeeRolesDTO> getData() { return services.getAllEmployeeRoles(); }

    @PostMapping("/AddEmployeeRoles")
    public ResponseEntity<?> NuevoEmployeeRole(@Valid @RequestBody EmployeeRolesDTO json, HttpServletRequest request){
        try{
            EmployeeRolesDTO respuesta = services.insertarDatos(json);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "insercion fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Los datos no pudieron ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "succes",
                    "data", respuesta
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "Error",
                            "message", "Error no controlado al registrar usuario",
                            "detail", e.getMessage()
                    ));
        }
    }
    @PutMapping("/UpdateEmployeeRole/{id}")
    public ResponseEntity<?> modificarUsuario(@PathVariable String id, @Valid @RequestBody EmployeeRolesDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            EmployeeRolesDTO dto = services.actualizarEmployeeRoles(id, json);
            return ResponseEntity.ok(dto);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Registro no encontrado",
                    "detail", e.getMessage()
            ));
        }
    }
    @DeleteMapping("/DeleteEmployeeRole/{id}")
    public ResponseEntity<?> eliminarEmployeeRole(@PathVariable String id){
        try{
            if (!services.eliminarEmployeeRole(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje error", "Registro no encontrado")
                        .body(Map.of(
                            "Error", "Not found",
                            "Mensaje", "El usuario no a sido encontrado",
                            "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok(Map.of(
                "status", "Proceso completado",
                "message", "Registro eliminado con exito"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error al eliminar el registro",
                    "detail", e.getMessage()
            ));
        }
    }
}
