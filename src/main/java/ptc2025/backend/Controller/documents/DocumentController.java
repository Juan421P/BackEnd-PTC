package ptc2025.backend.Controller.documents;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.documents.DocumentDTO;
import ptc2025.backend.Services.documents.DocumentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService servicio;

    @GetMapping("/getDocuments")
    public ResponseEntity<List<DocumentDTO>> obtenerDocumentos() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @PostMapping("/insertDocument")
    public ResponseEntity<?> insertar(@RequestBody @Valid DocumentDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "errores", result.getAllErrors()));
        }
        try {
            DocumentDTO creado = servicio.insertar(dto);
            return ResponseEntity.ok(Map.of("mensaje", "Documento registrado", "data", creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PutMapping("/updateDocument/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody @Valid DocumentDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Datos inválidos", "errores", result.getAllErrors()));
        }
        try {
            DocumentDTO actualizado = servicio.actualizar(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Documento actualizado", "data", actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Error interno al actualizar"));
        }
    }

    @DeleteMapping("/deleteDocument/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        boolean resultado = servicio.eliminar(id);
        if (resultado) {
            return ResponseEntity.ok(Map.of("mensaje", "Documento eliminado"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "No se encontró el documento"));
        }
    }
}
