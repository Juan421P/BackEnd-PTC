package ptc2025.backend.Controller.PensumSubject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Pensum.PensumDTO;
import ptc2025.backend.Models.DTO.PensumSubject.PensumSubjectDTO;
import ptc2025.backend.Services.PensumSubject.PensumSubjectService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/PensumSubjects")
public class PensumSubjectController {

    @Autowired
    PensumSubjectService service;

    @GetMapping("/getPenumSubjects")
    public List<PensumSubjectDTO> getPensa(){return service.getPensumSubjects();}

    @PostMapping("/insertPensumSubject")
    public ResponseEntity<?> newPensumSubject(@Valid @RequestBody PensumSubjectDTO json, HttpServletRequest request){
        try{
            PensumSubjectDTO response = service.insertPensumSubject(json);
            if(response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType","VALIDATION_ERROR",
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
                    "message", "Error no controlado al registrar la materia",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/updatePensumSubject/{ID}")
    public ResponseEntity<?> updatePensumSubject(@Valid String id, @RequestBody PensumSubjectDTO json, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            PensumSubjectDTO dto = service.updatePensumSubject(id,json);
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

    @DeleteMapping("/deletePensumSubject/{ID}")
    public ResponseEntity<?> deletePensumSubject(@PathVariable String id){
        try{
            if(!service.removePensumSubject(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Mensaje error", "Materia no encontrada").body(Map.of(
                        "Error", "Not found",
                        "Mensaje", "La materia no ha sido encontrada",
                        "Timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Materia eliminada exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar la materia",
                    "detail", e.getMessage()
            ));
        }
    }
}
