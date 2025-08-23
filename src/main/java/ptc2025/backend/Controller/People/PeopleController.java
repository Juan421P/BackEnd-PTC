package ptc2025.backend.Controller.People;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.People.PeopleDTO;
import ptc2025.backend.Services.People.PeopleService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/People")
public class PeopleController {

    @Autowired
    private PeopleService service;

    @GetMapping("/getPeople")
    public List<PeopleDTO> getPeople(){return service.getAllPeople();}

    @PostMapping("/newPeople")
    public ResponseEntity<?> newPeople(@Valid @RequestBody PeopleDTO json, HttpServletRequest request){
        try{
            PeopleDTO response = service.insertPeople(json);
            if(response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Los datos no pudierons ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "Success",
                    "data", response
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message", "Error no controlado al registrar una persona",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/updatePeople/{ID}")
    public ResponseEntity<?> updatePeople(@PathVariable String ID, @Valid @RequestBody PeopleDTO json, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            PeopleDTO dto = service.updatePeople(ID, json);
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

    @DeleteMapping("/deletePeople/{ID}")
    public ResponseEntity<?> deletePeople(@PathVariable String ID){
        try{
            if(!service.deletePeople(ID)){
                //Error
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje Error", "Persona no encontrada")
                        .body(Map.of(
                                "Error", "Not found",
                                "Mensaje", "La persona no ha sido encontrada",
                                "timestamp", Instant.now().toString()
                        ));
            }
            //Exit
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message","Persona eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status","Error",
                    "message", "Error al eliminar la persona",
                    "detail", e.getMessage()
            ));
        }
    }
}
