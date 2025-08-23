package ptc2025.backend.Controller.CodeTokens;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.CodeTokens.CodeTokensDTO;
import ptc2025.backend.Services.CodeTokens.CodeTokensService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/CodeToken")
public class CodeTokensController {
    @Autowired
    CodeTokensService service;

    //Get
    @GetMapping("/getCodeToke")
    public List<CodeTokensDTO> getCodeToke (){
        return service.getCodeToken();
    }

    //Post
    @PostMapping("/insertCodeToken")
    public ResponseEntity<Map<String, Object>> insertCodeToken(
            @Valid @RequestBody CodeTokensDTO dto,
            HttpServletRequest request){
        try {
            CodeTokensDTO respuesta = service.insertCodeToken(dto);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos del token invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar el token",
                    "detail", e.getMessage()
            ));
        }
    }
    //Put
    @PutMapping ("/uploadCodeToken/{id}")
    public ResponseEntity<?> uploadCodeToken(
            @PathVariable String id,
            @Valid @RequestBody CodeTokensDTO dto,
            BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            CodeTokensDTO actualizado =service.updateToken(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Token no encontrado",
                    "detail", e.getMessage()
            ));
        }
    }
    //Delete
    @DeleteMapping("/deleteCodeToken/{id}")
    public ResponseEntity <Map<String, Object>> deleteCodeToken(@PathVariable String id){
        try {
            if (!service.deleteToken(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Token no encontado")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "El token  no fue encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Token eliminado exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el token",
                    "detail", e.getMessage()
            ));
        }
    }
}
