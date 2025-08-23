package ptc2025.backend.Controller.InstrumentType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.InstrumentType.InstrumentTypeDTO;
import ptc2025.backend.Services.InstrumentType.InstrumentTypeService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/InstrumentType")
public class InstrumentTypeController {
    @Autowired
    InstrumentTypeService service;

    @GetMapping("/getInstrumentType")
    public List<InstrumentTypeDTO> getInstrumentType(){
        return service.getInstrumentType();
    }

    @PostMapping("/newInstrumentType")
    public ResponseEntity<Map<String, Object>> newInstrumentType(
            @Valid @RequestBody InstrumentTypeDTO dto,
            HttpServletRequest request){
        try {
            InstrumentTypeDTO respuesta = service.insertInstrumentType(dto);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos de tipo de instrumento invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar el tipo de instrumento",
                    "detail", e.getMessage()
            ));
        }

    }

    @PutMapping("/updateInstrumentType/{id}")
    public ResponseEntity<?> updateInstrumentType(
            @PathVariable String id,
            @Valid @RequestBody InstrumentTypeDTO dto,
            BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            InstrumentTypeDTO actualizado = service.updateInstrumentType(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Tipo de instrumento no encontrado",
                    "detail", e.getMessage()
            ));
        }

    }

    @DeleteMapping("/deleteInstrumentType/{id}")
    public ResponseEntity<Map<String, Object>> deleteInstrumentType(@PathVariable String id){
        try {
            if(!service.deleteInstrumentType(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Localidad no encontrada")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "Tipo de instrumento no encontrado",
                                "timestamp", Instant.now().toString()
                        ));

            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Tipo de instrumento eliminado exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar tipo de instrumento",
                    "detail", e.getMessage()
            ));
        }
    }

}
