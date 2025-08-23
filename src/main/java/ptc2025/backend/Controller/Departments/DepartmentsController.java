package ptc2025.backend.Controller.Departments;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Departments.DepartmentsDTO;
import ptc2025.backend.Services.Departments.DepartmentsService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Departments")
public class DepartmentsController {

    @Autowired
    private DepartmentsService service;

    @RequestMapping("/getDepartments")
    public List<DepartmentsDTO> getDepartments(){return service.getAllDepartments();}

    @PostMapping("/newDepartment")
    public ResponseEntity<?> newwDepartment(@Valid @RequestBody DepartmentsDTO json, HttpServletRequest request){
        try{
            DepartmentsDTO response = service.insertDepartment(json);
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
                    "message", "Error no controlado",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/updateDepartment/{ID}")
    public ResponseEntity<?> modificarUsuario(@PathVariable String ID, @Valid @RequestBody DepartmentsDTO json, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            //Creamos objeto de tipo DTO y se invoca al metodo acutlizarUsuario contenido en el Service
            DepartmentsDTO dto = service.updateDepartment(ID, json);
            //La API retorna una respuesta la cual contendra los datos en formato DTO
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

    @DeleteMapping("/deleteDeparment/{ID}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String ID){
        try{
            if(!service.deleteDepartment(ID)){
                //Error
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje Error", "Departamento no encontrado")
                        .body(Map.of(
                                "Error", "Not found",
                                "Mensaje", "El departamento no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            //Exit
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message","Departamentoq eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status","Error",
                    "message", "Error al eliminar el departamento",
                    "detail", e.getMessage()
            ));
        }
    }
}
