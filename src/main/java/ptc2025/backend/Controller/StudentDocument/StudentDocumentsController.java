package ptc2025.backend.Controller.StudentDocument;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.DataAuditLog.DataAuditLogDTO;
import ptc2025.backend.Models.DTO.StudentDocument.StudentDocumentsDTO;
import ptc2025.backend.Services.StudentDocument.StudentDocumentsService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiStudentsDocuements")
public class StudentDocumentsController {
    StudentDocumentsService service;

    @GetMapping("/getStudentsDocuements")
    public List<StudentDocumentsDTO> getStudentsDocuements() {
        return service.getStudentDocument();
    }

    @PostMapping("/newStudentsDocuements")
    public ResponseEntity<Map<String, Object>> newStudentsDocuementss(
            @Valid @RequestBody StudentDocumentsDTO dto,
            HttpServletRequest request) {
        try {
            StudentDocumentsDTO respuesta = service.insertStudentDocuments(dto);
            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos de los documentos invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar los documentos",
                    "detail", e.getMessage()
            ));
        }

    }

    @PutMapping("/updateStudentsDocuements/{id}")
    public ResponseEntity<?> updateDataAuditLog(
            @PathVariable String id,
            @Valid @RequestBody StudentDocumentsDTO dto,
            BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            StudentDocumentsDTO actualizado = service.updateStudentDocument(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Documentos no encontrados",
                    "detail", e.getMessage()
            ));
        }


    }
    @DeleteMapping("/deleteStudentsDocuements/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudentsDocuements( @PathVariable String id){
        try {
            if (!service.deleteStudentDocument(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Documentos no encontados")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "Los documentos no fueron encontrados",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Documentos eliminados exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar documentos",
                    "detail", e.getMessage()
            ));
        }
    }
}
