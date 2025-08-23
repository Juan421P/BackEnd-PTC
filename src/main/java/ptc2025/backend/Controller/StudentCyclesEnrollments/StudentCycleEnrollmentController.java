package ptc2025.backend.Controller.StudentCyclesEnrollments;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.studentCycleEnrollments.StudentCycleEnrollmentDTO;
import ptc2025.backend.Services.studentCycleEnrollments.StudentCycleEnrollmentService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/StudentCycleEnrollments")
public class StudentCycleEnrollmentController {

    @Autowired
    private StudentCycleEnrollmentService service;

    // GET
    @GetMapping("/getStudentCycleEnrollments")
    public List<StudentCycleEnrollmentDTO> getEnrollments() {
        return service.getEnrollments();
    }

    // POST
    @PostMapping("/insertStudentCycleEnrollment")
    public ResponseEntity<Map<String, Object>> insertEnrollment(@Valid @RequestBody StudentCycleEnrollmentDTO dto, BindingResult binding) {
        if (binding.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            binding.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "errors", errors));
        }
        try {
            StudentCycleEnrollmentDTO answer = service.insertEnrollment(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("status", "success", "data", answer));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "server error", "message", "Error interno del servidor", "detail", e.getMessage()));
        }
    }

    // PUT
    @PutMapping("/updateStudentCycleEnrollment/{id}")
    public ResponseEntity<Map<String, Object>> updateEnrollment(@PathVariable String id, @Valid @RequestBody StudentCycleEnrollmentDTO dto, BindingResult binding) {
        if (binding.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            binding.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "errors", errors));
        }
        try {
            StudentCycleEnrollmentDTO answer = service.updateEnrollment(id, dto);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("status", "success", "data", answer));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "server error", "message", "Error interno del servidor", "detail", e.getMessage()));
        }
    }

    // DELETE
    @DeleteMapping("/deleteStudentCycleEnrollment/{id}")
    public ResponseEntity<Map<String, Object>> deleteEnrollment(@PathVariable String id) {
        try {
            if (!service.deleteEnrollment(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", "error",
                                "message", "No se encontró la inscripción",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("status", "success", "message", "Inscripción eliminada con éxito"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "server error", "message", "Error interno del servidor", "detail", e.getMessage()));
        }
    }
}
