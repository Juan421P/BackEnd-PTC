package ptc2025.backend.Controller.SubjectTeachers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.SubjectTeachers.SubjectTeachersDTO;
import ptc2025.backend.Services.SubjectTeachers.SubjectTeachersService;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/SubjectTeachers")
public class SubjectTeachersController {

    @Autowired
    SubjectTeachersService service;

    @PostMapping("/newSubjectTeacher")
    public ResponseEntity<?> newSubjectTeacher(@Valid @RequestBody SubjectTeachersDTO json, HttpServletRequest request){
        try{
            SubjectTeachersDTO response = service.insertSubjectTeacher(json);
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
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message", "Error no controlado al registrar al maestro",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/updateSubjectTeacher/{ID}")
    public ResponseEntity<?> updateSubjectTeacher(@PathVariable String id, @Valid @RequestBody SubjectTeachersDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            SubjectTeachersDTO updatedSubjectTeacher = service.updateSubjectTeacher(id,dto);
            return ResponseEntity.ok(updatedSubjectTeacher);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos duplicados",
                    "campo", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/deleteSubjectTeacher/{ID}")
    public ResponseEntity<?> deleteSubjectTeacher(@PathVariable String id){
        try{
            if(!service.deleteSubjectTeacher(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("").body(Map.of(
                        "error", "Not Found",
                        "Message", "El docente no ha sido encontrado",
                        "timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Docente eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar al docente",
                    "detail", e.getMessage()
            ));
        }
    }
}
