package ptc2025.backend.Controller.SocialService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ptc2025.backend.Models.DTO.SocialService.SocialServiceDTO;
import ptc2025.backend.Services.SocialService.SocialServiceServices;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/SocialService")
public class SocialServiceController {
    @Autowired
    private SocialServiceServices services;

    @GetMapping("/getDataSocialService")
    public List<SocialServiceDTO> getSocialService(){
        return services.getSocialService();
    }
    @PostMapping("/newSocialService")
    public ResponseEntity<Map<String, Object>> registrarServicioSocial(
            @Valid @RequestBody SocialServiceDTO dtoServicio,
            HttpServletRequest request){
        try{
            SocialServiceDTO respuesta = services.insertarServicioSocial(dtoServicio);
            if(respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message" , "Datos de proyecto de servicio social invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al resgistar el proyecto de servicio social",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/updateSocialService/{id}")
    public ResponseEntity<?> modificarServicioSocial(
            @PathVariable String id,
            @Valid @RequestBody SocialServiceDTO dto,
            BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            SocialServiceDTO actualizado = services.modificarServicioSocial(id, dto);
            return ResponseEntity.ok().body(Map.of(
                    "status", "sucess",
                    "datos", actualizado
            ));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Proyecto de servicio social no encontrado",
                    "detail", e.getMessage()
            ));
        }
    }
    @DeleteMapping("/deleteSocialService/{id}")
    public ResponseEntity<Map<String, Object>> eliminarLocalidad(@PathVariable String id){
        try{
            if(!services.eliminarServicioSocial(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Message-Error", "Localidad no encontrada")
                        .body(Map.of(
                                "error", "Not found",
                                "message", "El proyecto de servicio social no fue encontrad",
                                "timestamp", Instant.now().toString()
                        ));

            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Proyecto de servicio social eliminado exitosamente"  // Mensaje de éxito
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el proyecto de servicio social",
                    "detail", e.getMessage()
            ));
        }
    }
}
