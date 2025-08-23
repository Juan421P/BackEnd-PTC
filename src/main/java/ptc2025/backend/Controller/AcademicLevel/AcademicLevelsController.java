package ptc2025.backend.Controller.AcademicLevel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.AcademicLevel.AcademicLevelsDTO;
import ptc2025.backend.Models.DTO.CourseOfferings.CourseOfferingsDTO;
import ptc2025.backend.Services.AcademicLevel.AcademicLevelService;
import ptc2025.backend.Services.CourseOfferings.CourseOfferingsService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/AcademicLevels")
public class AcademicLevelsController {

    @Autowired
    AcademicLevelService service;

    @GetMapping("/getAcademicLevels")
    public List<AcademicLevelsDTO> getTeachers(){ return service.getAllLevels();}

    @PostMapping("/insertAcademicLevel")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody AcademicLevelsDTO usuario, HttpServletRequest request){
        try{
            AcademicLevelsDTO respuesta = service.insertLevel(usuario);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del maestro inválidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status","success",
                    "data",respuesta));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar el maestro",
                    "detail", e.getMessage()
            ));
        }
    }

    @PostMapping("/updateAcademicLevel/{ID}")
    public ResponseEntity<?> updateCourseTeacher(@PathVariable String id, @Valid @RequestBody AcademicLevelsDTO json, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            AcademicLevelsDTO dto = service.updateLevel(id, json);
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

    @DeleteMapping("/deleteAcademicLevel/{ID}")
    public ResponseEntity<?> deleteCourseTeacher(@PathVariable String ID){
        try{
            if(!service.deleteLevel(ID)){
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
