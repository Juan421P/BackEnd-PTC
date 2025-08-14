package ptc2025.backend.Controller.userRoles;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.userRoles.userRolesDTO;
import ptc2025.backend.Services.userRoles.userRolesService;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/apiUserRoles")
public class userRolesController {
    @Autowired
    userRolesService servicio;

    @GetMapping("/getRoles")
    public ResponseEntity<List<userRolesDTO>> obtenerRoles() {
        return ResponseEntity.ok(servicio.getUserRole());
    }

    @PostMapping("/insertRole")
    public ResponseEntity<?> insertar(@RequestBody @Valid userRolesDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensaje",
                    "Datos inválidos",
                    "errores", result.getAllErrors()));
        }
        try {
            userRolesDTO creado = servicio.insertUserRole(dto);
            return ResponseEntity.ok(Map.of(
                    "mensaje",
                    "Rol registrado",
                    "data", creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensaje", e.getMessage()));
        }
    }

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody @Valid userRolesDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensaje",
                    "Datos inválidos",
                    "error", result.getAllErrors()));
        }
        try {
            userRolesDTO actualizado = servicio.updateUserRole(id, dto);
            return ResponseEntity.ok(Map.of(
                    "mensaje",
                    "Rol actualizado",
                    "data", actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensaje", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "mensaje",
                    "Error al actualizar"));
        }
    }

    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        boolean resultado = servicio.deleteUserRole(id);
        if (resultado) {
            return ResponseEntity.ok(Map.of(
                    "mensaje",
                    "Rol eliminado"));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensaje",
                    "No se encontró el rol de usuario"));
        }
    }
}


