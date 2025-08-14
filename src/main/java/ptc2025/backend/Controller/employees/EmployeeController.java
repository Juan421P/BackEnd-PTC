package ptc2025.backend.Controller.employees;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.employees.EmployeeDTO;
import ptc2025.backend.Services.employees.EmployeeService;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService servicio;

    @GetMapping("/getEmployees")
    public ResponseEntity<List<EmployeeDTO>> obtenerTodos() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @PostMapping("/insertEmployee")
    public ResponseEntity<?> insertar(@RequestBody @Valid EmployeeDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }
        try {
            EmployeeDTO creado = servicio.insertar(dto);
            return ResponseEntity.ok(Map.of("mensaje", "Empleado registrado", "data", creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody @Valid EmployeeDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }
        try {
            EmployeeDTO actualizado = servicio.actualizar(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Empleado actualizado", "data", actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Error al actualizar"));
        }
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        boolean resultado = servicio.eliminar(id);
        if (resultado) {
            return ResponseEntity.ok(Map.of("mensaje", "Empleado eliminado"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "No se encontró el empleado"));
        }
    }
}
