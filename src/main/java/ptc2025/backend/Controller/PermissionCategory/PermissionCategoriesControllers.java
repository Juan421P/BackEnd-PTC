package ptc2025.backend.Controller.PermissionCategory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.PermissionCategory.PermissionCategoriesDTO;
import ptc2025.backend.Services.PermissionCategory.PermissionCategoriesService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/PermissionCategories")
public class PermissionCategoriesControllers {

    @Autowired
    private PermissionCategoriesService service;

    @GetMapping("/getPermissionCategories")
    public List<PermissionCategoriesDTO> getPermissions(){return service.getAllPermissions();}

    @PostMapping("/newPermissionCategories")
    public ResponseEntity<?> newCategory (@Valid @RequestBody PermissionCategoriesDTO json, HttpServletRequest request){
        try{
            PermissionCategoriesDTO response = service.insertPermission(json);
            if(response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "los datos no pudieron ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "Success",
                    "data", response
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message","Error al controlado al registrar un permiso",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/updatePermissionCategories/{ID}")
    public ResponseEntity<?> modificarUsuario(@PathVariable String ID, @Valid @RequestBody PermissionCategoriesDTO json, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            PermissionCategoriesDTO dto = service.updatePermission(ID, json);
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

    @DeleteMapping("/deletePermissionCategories/{ID}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String ID){
        try{
            if(!service.deletePermission(ID)){
                //Error
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje Error", "Permiso no encontrado")
                        .body(Map.of(
                                "Error", "Not found",
                                "Mensaje", "El permiso no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            //Exit
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message","Permiso eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status","Error",
                    "message", "Error al eliminar el permiso",
                    "detail", e.getMessage()
            ));
        }
    }
}
