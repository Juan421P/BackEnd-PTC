package ptc2025.backend.Controller.studentCyclesEnrollments;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.studentCycleEnrollments.StudentCycleEnrollmentDTO;
import ptc2025.backend.Services.studentCycleEnrollments.StudentCycleEnrollmentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studentCycleEnrollments")
@RequiredArgsConstructor
@Slf4j
public class StudentCycleEnrollmentController {

    private final StudentCycleEnrollmentService servicio;

    @GetMapping("/getEnrollments")
    public ResponseEntity<List<StudentCycleEnrollmentDTO>> obtenerTodos() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @PostMapping("/insertEnrollment")
    public ResponseEntity<?> insertar(@RequestBody @Valid StudentCycleEnrollmentDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }

        try {
            StudentCycleEnrollmentDTO creado = servicio.insertar(dto);
            return ResponseEntity.ok(Map.of("mensaje", "Inscripción registrada", "data", creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PutMapping("/updateEnrollment/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody @Valid StudentCycleEnrollmentDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }

        try {
            StudentCycleEnrollmentDTO actualizado = servicio.actualizar(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Inscripción actualizada", "data", actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Error al actualizar"));
        }
    }

    @DeleteMapping("/deleteEnrollment/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        boolean resultado = servicio.eliminar(id);
        if (resultado) {
            return ResponseEntity.ok(Map.of("mensaje", "Inscripción eliminada"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "No se encontró la inscripción"));
        }
    }
}
