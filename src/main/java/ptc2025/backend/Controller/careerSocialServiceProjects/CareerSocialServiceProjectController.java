package ptc2025.backend.Controller.careerSocialServiceProjects;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.careerSocialServiceProjects.CareerSocialServiceProjectDTO;
import ptc2025.backend.Services.careerSocialServiceProjects.CareerSocialServiceProjectService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/careerSocialServiceProjects")
@RequiredArgsConstructor
@Slf4j
public class CareerSocialServiceProjectController {

    private final CareerSocialServiceProjectService servicio;

    @GetMapping("/getProjects")
    public ResponseEntity<List<CareerSocialServiceProjectDTO>> obtenerProyectos() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @PostMapping("/insertProject")
    public ResponseEntity<?> insertar(@RequestBody @Valid CareerSocialServiceProjectDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }
        try {
            CareerSocialServiceProjectDTO creado = servicio.insertar(dto);
            return ResponseEntity.ok(Map.of("mensaje", "Proyecto registrado", "data", creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PutMapping("/updateProject/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody @Valid CareerSocialServiceProjectDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "error", result.getAllErrors()));
        }
        try {
            CareerSocialServiceProjectDTO actualizado = servicio.actualizar(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Proyecto actualizado", "data", actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Error interno al actualizar"));
        }
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        boolean resultado = servicio.eliminar(id);
        if (resultado) {
            return ResponseEntity.ok(Map.of("mensaje", "Proyecto eliminado"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "No se encontró el proyecto"));
        }
    }
}
