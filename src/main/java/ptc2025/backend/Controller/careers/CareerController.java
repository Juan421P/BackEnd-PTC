package ptc2025.backend.Controller.careers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.careers.CareerDTO;
import ptc2025.backend.Services.careers.CareerService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/careers")
@RequiredArgsConstructor
@Slf4j
public class CareerController {

    private final CareerService servicio;

    @GetMapping("/getCareers")
    public ResponseEntity<List<CareerDTO>> obtenerCarreras() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @PostMapping("/insertCareer")
    public ResponseEntity<?> insertar(@RequestBody @Valid CareerDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }
        try {
            CareerDTO creado = servicio.insertar(dto);
            return ResponseEntity.ok(Map.of("mensaje", "Carrera registrada", "data", creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PutMapping("/updateCareer/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody @Valid CareerDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }
        try {
            CareerDTO actualizado = servicio.actualizar(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Carrera actualizada", "data", actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Error al actualizar"));
        }
    }

    @DeleteMapping("/deleteCareer/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        boolean resultado = servicio.eliminar(id);
        if (resultado) {
            return ResponseEntity.ok(Map.of("mensaje", "Carrera eliminada"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "No se encontró la carrera"));
        }
    }
}

