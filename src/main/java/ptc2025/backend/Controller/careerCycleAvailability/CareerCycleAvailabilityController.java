package ptc2025.backend.Controller.careerCycleAvailability;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.careerCycleAvailability.CareerCycleAvailabilityDTO;
import ptc2025.backend.Services.careerCycleAvailability.CareerCycleAvailabilityService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/careerCycleAvailability")
@RequiredArgsConstructor
@Slf4j
public class CareerCycleAvailabilityController {

    private final CareerCycleAvailabilityService servicio;

    @GetMapping("/getAvailability")
    public ResponseEntity<List<CareerCycleAvailabilityDTO>> obtenerDisponibilidad() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @PostMapping("/insertAvailability")
    public ResponseEntity<?> insertar(@RequestBody @Valid CareerCycleAvailabilityDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }
        try {
            CareerCycleAvailabilityDTO creado = servicio.insertar(dto);
            return ResponseEntity.ok(Map.of("mensaje", "Disponibilidad registrada", "data", creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PutMapping("/updateAvailability/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody @Valid CareerCycleAvailabilityDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }
        try {
            CareerCycleAvailabilityDTO actualizado = servicio.actualizar(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Disponibilidad actualizada", "data", actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Error interno al actualizar"));
        }
    }

    @DeleteMapping("/deleteAvailability/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        boolean resultado = servicio.eliminar(id);
        if (resultado) {
            return ResponseEntity.ok(Map.of("mensaje", "Disponibilidad eliminada"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "No se encontró la disponibilidad"));
        }
    }
}
