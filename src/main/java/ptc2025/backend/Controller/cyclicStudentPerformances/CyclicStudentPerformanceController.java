package ptc2025.backend.Controller.cyclicStudentPerformances;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.cyclicStudentPerformances.CyclicStudentPerformanceDTO;
import ptc2025.backend.Services.cyclicStudentPerformances.CyclicStudentPerformanceService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cyclicStudentPerformances")
@RequiredArgsConstructor
@Slf4j
public class CyclicStudentPerformanceController {

    private final CyclicStudentPerformanceService servicio;

    @GetMapping("/getPerformances")
    public ResponseEntity<List<CyclicStudentPerformanceDTO>> obtenerTodos() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @PostMapping("/insertPerformance")
    public ResponseEntity<?> insertar(@RequestBody @Valid CyclicStudentPerformanceDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensaje", "Datos inválidos",
                    "error", result.getAllErrors()
            ));
        }

        try {
            CyclicStudentPerformanceDTO creado = servicio.insertar(dto);
            return ResponseEntity.ok(Map.of("mensaje", "Rendimiento registrado", "data", creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PutMapping("/updatePerformance/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody @Valid CyclicStudentPerformanceDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }

        try {
            CyclicStudentPerformanceDTO actualizado = servicio.actualizar(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Rendimiento actualizado", "data", actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Error interno al actualizar"));
        }
    }

    @DeleteMapping("/deletePerformance/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        boolean resultado = servicio.eliminar(id);
        if (resultado) {
            return ResponseEntity.ok(Map.of("mensaje", "Rendimiento eliminado"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "No se encontró el rendimiento"));
        }
    }
}

