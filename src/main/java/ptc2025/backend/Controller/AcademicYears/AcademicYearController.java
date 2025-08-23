package ptc2025.backend.Controller.AcademicYears;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.AcademicYear.AcademicYearDTO;
import ptc2025.backend.Services.AcademicYear.AcademicYearService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/AcademicYear")
public class AcademicYearController {
    @Autowired
    private AcademicYearService service;

    @GetMapping("/getAcademicYear")
    public List<AcademicYearDTO> getAcademicYear(){
        return service.getAcademicYear();
    }

    @PostMapping("/insertAcademicYear")
    public ResponseEntity<Map<String, Object>> insertAcademicYear(@Valid @RequestBody AcademicYearDTO dto, HttpServletRequest request){
        try{
            AcademicYearDTO answer = service.insertAcademicYear(dto);
            if(answer==null){
                return ResponseEntity.status(HttpStatus. BAD_REQUEST)
                        .body(Map.of(
                                "status", "error",
                                "error type", "VALIDATION_ERROR" ,
                                "message","Error al insertar un plan de evaluación"
                        ));
            }
            return ResponseEntity.status((HttpStatus.CREATED))
                    .body(Map.of(
                            "status", "success",
                            "dara", answer
                    ));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "server error",
                            "message", "Error interno del servidor",
                            "detail", e.getMessage()
                    ));
        }
    }
    @PutMapping("/updateEvaluationPlan/{id}")
    public ResponseEntity<?> updateAcademicYear(@PathVariable String id, @Valid @RequestBody AcademicYearDTO dto, BindingResult binding ){
        if(binding.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            binding.getFieldErrors().forEach(error ->
                    errors.put(error.getField(),error.
                            getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);

        }
        try{
            AcademicYearDTO answer =  service.updateAcademicYear(id, dto);
            if(answer == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(
                                "status", "error",
                                "error type", "VALIDATION_ERROR" ,
                                "message","Error al actualizar un plan de evaluación"
                        ));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of(
                            "status", "success",
                            "data", answer
                    ));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "server error",
                            "message", "Error interno del servidor",
                            "detail", e.getMessage()
                    ));
        }
    }

    @DeleteMapping("/deleteAcademicYear/{id}")
    public ResponseEntity<Map<String, Object>> deleteAcademicYear(@PathVariable String id){
        try{
            if(!service.deleteAcademicYear(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "errror", "not found",
                                "message", "No se encontro el plan de evaluacion",
                                "timestramp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of(
                            "status", "process completed",
                            "message", "Plan de evaluacion eliminado con exito"
                    ));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "server error",
                            "message", "Error interno del servidor",
                            "detail", e.getMessage()
                    ));
        }
    }
}
