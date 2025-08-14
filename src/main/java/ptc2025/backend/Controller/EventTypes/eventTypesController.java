package ptc2025.backend.Controller.EventTypes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.EventTypes.eventTypesDTO;
import ptc2025.backend.Services.EventTypes.eventTypesService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiEventTypes")
public class eventTypesController {
    @Autowired
    eventTypesService service;

    //Get
    @GetMapping("/getDataEventType")
    public List<eventTypesDTO> getEventTypes(){
        return service.getEventTypes();
    }
    //Post
    @PostMapping("/newEventType")
    public ResponseEntity<Map<String, Object>> registrarTipoEvento(
            @Valid @RequestBody eventTypesDTO dtoEvento,
            HttpServletRequest request){
        try{
            eventTypesDTO respuesta = service.insertarEvento(dtoEvento);
            if(respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos del evento invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar el evento",
                    "detail", e.getMessage()
            ));
        }
    }

    //PUT
    @PutMapping("/uploadEventTypes/{id}")
    public ResponseEntity <?> modificarTipoEvento(
            @PathVariable String id,
            @Valid @RequestBody eventTypesDTO dto,
            BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            eventTypesDTO actualizado =service.modificarEvento(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Evento no encontrada",
                    "detail", e.getMessage()
            ));
        }
    }
    @DeleteMapping("/deleteEventType/{id}")
    public ResponseEntity <Map<String, Object>> eliminarTipoEvento(@PathVariable String id){
        try {
            if (!service.eliminarEvento(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Tipo de Evento no encontado")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "El tipo  no fue encontrada",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Evento eliminado exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar universidad",
                    "detail", e.getMessage()
            ));
        }
    }
}


