package ptc2025.backend.Models.DTO.Notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NotificationDTO {
    private String notificationID;
    @NotNull
    private String userID;
    @NotBlank @NotNull (message = "El titulo no puede estar vacio")
    private String title;
    @Size(max = 300, message = "La descripción no debe exceder los 300 caracteres")
    private String body;
    @NotBlank (message = "Ingrese la fecha de envio")
    private LocalDate sentAt;
    @NotBlank (message = "Ingrese la fecha de lectura ")
    private LocalDate readAt;
}
/**NOTIFICATIONID
 USERID
 TITLE
 BODY
 SENTAT
 READAT*/

/**notificationID
 userID not null
 title not null
 body
 sentAt
 readAt*/