package ptc2025.backend.Services.Notification;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ptc2025.backend.Entities.Notification.NotificationEntity;
import ptc2025.backend.Entities.PlanComponents.PlanComponentsEntity;
import ptc2025.backend.Models.DTO.Notification.NotificationDTO;
import ptc2025.backend.Respositories.Notification.NotificationRespository;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class NotificationService {
    @Autowired
    NotificationRespository repo;
    //Get
    public List<NotificationDTO> getNotification(){
        List<NotificationEntity> notification = repo.findAll();
        return notification.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    //post
    public NotificationDTO insertNotification(NotificationDTO dto) {
        // Validaciones
        if (dto == null ||
                dto.getUserID() == null || dto.getUserID().isBlank() ||
                dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("userID y title son obligatorios");
        }

        if (dto.getTitle().length() > 100) {
            throw new IllegalArgumentException("El título no puede tener más de 100 caracteres");
        }

        if (dto.getBody() != null && dto.getBody().length() > 300) {
            throw new IllegalArgumentException("El cuerpo del mensaje no puede exceder 300 caracteres");
        }

        try {
            NotificationEntity entity = convertirAEntity(dto);
            NotificationEntity saved = repo.save(entity);
            return convertirADTO(saved);
        } catch (Exception e) {
            log.error("Error al guardar la notificación: " + e.getMessage());
            throw new RuntimeException("Error interno al guardar la notificación");
        }
    }
    //Put
    public NotificationDTO updateNotification (String id, NotificationDTO dto){
        NotificationEntity existente = new NotificationEntity();
        existente.setUserID(dto.getUserID());
        existente.setTitle(dto.getTitle());
        existente.setBody(dto.getBody());
        existente.setSentAt(dto.getSentAt());
        existente.setReadAt(dto.getReadAt());
        NotificationEntity actualizar = repo.save(existente);
        return convertirADTO(actualizar);

    }
    //Delete
    public boolean deleteNotification(String id){
        try {
            NotificationEntity objCompo = repo.findById(id).orElse(null);
            if (objCompo != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Notificación no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontraron notificaciones con el ID: " + id , 1);

        }
    }
    //Convertir a DTO
    private NotificationDTO convertirADTO (NotificationEntity entity){
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationID(entity.getNotificationID());
        dto.setUserID(entity.getUserID());
        dto.setTitle(entity.getTitle());
        dto.setBody(entity.getBody());
        dto.setSentAt(entity.getSentAt());
        dto.setReadAt(entity.getReadAt());
        return dto;
    }
    //Convertir a Entity
    private NotificationEntity convertirAEntity (NotificationDTO dto){
        NotificationEntity entity = new NotificationEntity();
        entity.setUserID(dto.getUserID());
        entity.setTitle(dto.getTitle());
        entity.setBody(dto.getBody());
        entity.setSentAt(dto.getSentAt());
        entity.setReadAt(dto.getReadAt());
        return entity;
    }
}
