package ptc2025.backend.Controller.SubjectFamilies;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.SubjectFamilies.SubjectFamiliesDTO;
import ptc2025.backend.Services.SubjectFamilies.SubjectFamiliesService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/SubjectFamilies")
public class SubjectFamiliesController {

    @Autowired
    SubjectFamiliesService service;

    @GetMapping("/getSubjectFamilies")
    public List<SubjectFamiliesDTO> geSubjecttFamily(){return service.getSubjectFam();}

    @PostMapping("/newSubjectFamilies")
    public ResponseEntity<?> newSubjectFam(@Valid @RequestBody SubjectFamiliesDTO json, HttpServletRequest request){
        try{
            SubjectFamiliesDTO response = service.insertSubjectFam(json);
            if(response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Error",
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
                    "message", "Error no controlado al registrar la familia de materias.",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/updateSubjectFamilies/{ID}")
    public ResponseEntity<?> updateFamily(@PathVariable String ID, @Valid @RequestBody SubjectFamiliesDTO json, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            SubjectFamiliesDTO dto = service.updateSubjectFam(ID, json);
            return ResponseEntity.ok(dto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "Error", "Datos duplicados"
            ));
        }
    }

    @DeleteMapping("/deleteSubjectFamilies/{ID}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String ID){
        try{
            if(!service.deleteSubjectFam(ID)){
                //Error
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje Error", "Familia no encontrada")
                        .body(Map.of(
                                "Error", "Not found",
                                "Mensaje", "Familia de materias no encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            //Exit
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message","Familia de materias eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status","Error",
                    "message", "Error la familia de materias",
                    "detail", e.getMessage()
            ));
        }
    }
}
