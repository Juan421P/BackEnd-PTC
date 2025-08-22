package ptc2025.backend.Controller.Notification;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Notification.NotificationDTO;
import ptc2025.backend.Models.DTO.PlanComponents.PlanComponentsDTO;
import ptc2025.backend.Services.Notification.NotificationService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiNotifications")
public class NotificationController {
    @Autowired
    NotificationService service;
    @GetMapping("/getEvaluationPlanComponents")
    public List<NotificationDTO> getNotification(){
        return  service.getNotification();
    }
    @PostMapping("/newNotification")
    public ResponseEntity<Map<String, Object>> newNotification(
            @Valid @RequestBody NotificationDTO dto,
            HttpServletRequest request){
        try {
            NotificationDTO respuesta = service.insertNotification(dto);
            if(respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Notificacion invalida"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar la notificacion",
                    "detail", e.getMessage()
            ));
        }

    }
    @PutMapping("/updateNotification/{id}")
    public ResponseEntity<?> updateNotification (
            @PathVariable String id,
            @Valid @RequestBody NotificationDTO dto,
            BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            NotificationDTO actualizado =service.updateNotification(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Notificación no encontrada",
                    "detail", e.getMessage()
            ));
        }

    }
    @DeleteMapping("/deleteNotification/{id}")
    public ResponseEntity<Map<String, Object>> deleteNotification( @PathVariable String id){
        try {
            if (!service.deleteNotification(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Tipo de notificación no encontada")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "La notificación no fue encontrada",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Notificación eliminada exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar notificación",
                    "detail", e.getMessage()
            ));
        }
    }
}
