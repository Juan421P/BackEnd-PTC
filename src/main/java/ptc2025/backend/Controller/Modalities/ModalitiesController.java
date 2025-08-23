package ptc2025.backend.Controller.Modalities;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.Modalities.ModalitiesDTO;
import ptc2025.backend.Services.Modalities.ModalityService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Modalities")
public class ModalitiesController {
    @Autowired
    private ModalityService service;

    @GetMapping("/getModalities")
    public List<ModalitiesDTO> getData() { return service.getAllModalities(); }

    @PostMapping("/insertModality")
    public ResponseEntity<?> nuevaModality(@Valid @RequestBody ModalitiesDTO json, HttpServletRequest request){
        try {
            ModalitiesDTO respuesta = service.insertarDatos(json);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "insercion fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Los datos no pudieron ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "succes",
                "data", respuesta
            ));
        }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                    "status", "Error",
                    "message", "Error no controlado al ingresar el nuevo registro",
                    "detail", e.getMessage()
                ));
        }
    }

    @PutMapping("/updateModalities/{id}")
    public ResponseEntity<?> modificarModalities(@PathVariable String id, @Valid @RequestBody ModalitiesDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            ModalitiesDTO dto = service.actualizarDatos(id, json);
            return ResponseEntity.ok(dto);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Registro no encontrado",
                    "detail", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/deleteModality/{id}")
    public ResponseEntity<?> eliminarModality(@PathVariable String id){
        try{
            if (!service.eliminarModality(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje error", "Modality no encontrada")
                        .body(Map.of(
                            "Error", "Not found",
                            "Mensaje", "El usuario no ha sido encontrado",
                            "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok(Map.of(
                "status", "Proceso Completado",
                "mmessage", "Modality eliminada con exito"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error al eliminar la Modalidad",
                    "detail", e.getMessage()
            ));
        }
    }
}
