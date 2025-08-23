package ptc2025.backend.Controller.Pensum;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Pensum.PensumDTO;
import ptc2025.backend.Services.Pensum.PensumService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Pensum")
public class PensumController {

    @Autowired
    PensumService service;

    @GetMapping("/getPensa")
    public List<PensumDTO> getPensa(){return service.getPensa();}

    @PostMapping("/newPensum")
    public ResponseEntity<?> newPensum(@Valid @RequestBody PensumDTO json, HttpServletRequest request){
        try{
            PensumDTO response = service.insertPensum(json);
            if(response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Los datos no pudieron ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "Success",
                    "data", response
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message", "Error no controlado al registrar el pensum.",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/updatePensum/{ID}")
    public ResponseEntity<?> updatePensum(@PathVariable String ID, @Valid @RequestBody PensumDTO json, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        }
        try{
            PensumDTO dto = service.updatePensum(ID, json);
            return ResponseEntity.ok(dto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "Error", "Datos duplicados",
                    "Campo", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/deletePensum/{ID}")
    public ResponseEntity<?> deletePensum(@PathVariable String ID){
        try{
            if(!service.deletePensum(ID)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message Error", "Usuario no encontrado")
                        .body(Map.of(
                                "Error", "Not found",
                                "Mensaje", "El pensum no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Pensum eliminado con exito"
            ));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el pensum",
                    "detail", e.getMessage()
            ));
        }
    }
}
