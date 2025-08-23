package ptc2025.backend.Controller.Students;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Students.StudentsDTO;
import ptc2025.backend.Services.Students.StudentsService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Students")
public class StudentsController {

    @Autowired
    private StudentsService service;

    @GetMapping("/getStudents")
    public List<StudentsDTO> getStudents(){return service.getAllStudents();}

    @PostMapping("/newStudent")
    public ResponseEntity<?> newStudent(@Valid @RequestBody StudentsDTO json, HttpServletRequest request){
        try{
            StudentsDTO response = service.insertStudent(json);
            if (response == null){
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
                    "message", "Error no controlado al registrar usuario",
                    "detail", e.getMessage()
            ));
        }
    }

    @PostMapping("/updateStudents/{ID}")
    public ResponseEntity<?> updateStudent(@PathVariable String ID, @Valid @RequestBody StudentsDTO json, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            StudentsDTO dto = service.updateStudent(ID, json);
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

    @DeleteMapping("/deleteStudents/{ID}")
    public ResponseEntity<?> deleteStudent(@PathVariable String ID){
        try{
            if(!service.deleteStudent(ID)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje Error", "Estudiante no encontrado")
                        .body(Map.of(
                                "Error", "Not found",
                                "Menaje", "El estudiante no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Estudiante eliminado con exito"
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
