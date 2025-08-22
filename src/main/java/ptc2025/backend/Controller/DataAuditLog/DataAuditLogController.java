package ptc2025.backend.Controller.DataAuditLog;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.DataAuditLog.DataAuditLogDTO;
import ptc2025.backend.Models.DTO.PlanComponents.PlanComponentsDTO;
import ptc2025.backend.Services.DataAuditLog.DataAuditLogService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiDataAuditLog")
public class DataAuditLogController {
    @Autowired
    private DataAuditLogService service;
    @GetMapping("/getDataAuditLog")
    public List<DataAuditLogDTO> getDataAuditLog(){
        return  service.getDataAuditLog();
    }


    @PostMapping("/newDataAuditLog")
    public ResponseEntity<Map<String, Object>> newEvaluationPlanComponents(
            @Valid @RequestBody DataAuditLogDTO dto,
            HttpServletRequest request){
        try {
            DataAuditLogDTO respuesta = service.insertDataAuditLog(dto);
            if(respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos de la audiotoria invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar la auditoria",
                    "detail", e.getMessage()
            ));
        }

    }
    @PutMapping("/updateDataAuditLog/{id}")
    public ResponseEntity<?> updateDataAuditLog (
            @PathVariable String id,
            @Valid @RequestBody DataAuditLogDTO dto,
            BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            DataAuditLogDTO actualizado =service.updateDataAuditLog(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Componente de la auditoia no encontrados",
                    "detail", e.getMessage()
            ));
        }

    }
    @DeleteMapping("/deleteDataAuditLog/{id}")
    public ResponseEntity<Map<String, Object>> deleteDataAuditLog( @PathVariable String id){
        try {
            if (!service.deleteDataAuditLog(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Auditoria no encontada")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "La auditoria no fue encontrada",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Auditoria eliminada exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar auditoria",
                    "detail", e.getMessage()
            ));
        }
    }
}
//DataAuditLog