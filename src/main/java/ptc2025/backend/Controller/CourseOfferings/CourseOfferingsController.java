package ptc2025.backend.Controller.CourseOfferings;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.CourseOfferings.CourseOfferingsDTO;
import ptc2025.backend.Models.DTO.CourseOfferingsTeachers.CourseOfferingsTeachersDTO;
import ptc2025.backend.Services.CourseOfferings.CourseOfferingsService;
import ptc2025.backend.Services.CourseOfferingsTeachers.CourseOfferingsTeachersService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/CourseOfferings")
public class CourseOfferingsController {

    @Autowired
    CourseOfferingsService service;

    @GetMapping("/getAllCourseOfferings")
    public List<CourseOfferingsDTO> getCourses(){ return service.getAllCourses();}

    @PostMapping("/insertCourseOffering")
    public ResponseEntity<?> insertCourse(@Valid @RequestBody CourseOfferingsDTO usuario, HttpServletRequest request){
        try{
            CourseOfferingsDTO respuesta = service.insertCourse(usuario);
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

    @PostMapping("/updateCourseOffering/{ID}")
    public ResponseEntity<?> updateCourse(@PathVariable String id, @Valid @RequestBody CourseOfferingsDTO json, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            CourseOfferingsDTO dto = service.updateCourse(id, json);
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

    @DeleteMapping("/deleteCourseOffering/{ID}")
    public ResponseEntity<?> deleteCourse(@PathVariable String ID){
        try{
            if(!service.deleteCourse(ID)){
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
