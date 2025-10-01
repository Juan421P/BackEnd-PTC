package ptc2025.backend.Controller.EvaluationInstruments;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.CourseOfferingsTeachers.CourseOfferingsTeachersDTO;
import ptc2025.backend.Models.DTO.EvaluationInstruments.EvaluationInstrumentsDTO;
import ptc2025.backend.Services.EvaluationInstruments.EvaluationInstrumentsService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/EvaluationInstrument")
@CrossOrigin
public class EvaluationInstrumentsController {

    @Autowired
    EvaluationInstrumentsService service;

    @GetMapping("/getEvaluationInstruments")
    public List<EvaluationInstrumentsDTO> getEvaluationInstruments(){return service.getAllEvaluations();}

    @GetMapping("/getEvaluationInstrumentsPagination")
    public ResponseEntity<Page<EvaluationInstrumentsDTO>> getEvalutionInstrumentPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<EvaluationInstrumentsDTO> levels = service.getEvaluationInstrumentPagination(page, size);

        if(levels.isEmpty()){
            return ResponseEntity.badRequest().body(Page.empty());
        }
        return ResponseEntity.ok(levels);
    }

    @PostMapping("/insertEvaluationInstrument")
    public ResponseEntity<?> insertEvaluationInstrument(@Valid @RequestBody EvaluationInstrumentsDTO json, HttpServletRequest request){
        try{
            EvaluationInstrumentsDTO response = service.insertEvaluation(json);
            if (response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del maestro inválidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status","success",
                    "data",response));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar el maestro",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/updateEvaluationInstrument/{id}")
    public ResponseEntity<?> updateEvaluationInstrument(@PathVariable String id, @Valid @RequestBody EvaluationInstrumentsDTO json, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            EvaluationInstrumentsDTO dto = service.updateEvaluation(id, json);
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

    @DeleteMapping("/deleteEvaluationInstrument/{id}")
    public ResponseEntity<?> deleteEvaluationInstrument(@PathVariable String id){
        try{
            if(!service.deleteEvaluation(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje Error","Evaluación no encontrada").body(Map.of(
                                "Error", "Not found",
                                "Menaje", "La evaluación no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Evaluación eliminado con exito"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el estudiante",
                    "detail", e.getMessage()
            ));
        }
    }
}
