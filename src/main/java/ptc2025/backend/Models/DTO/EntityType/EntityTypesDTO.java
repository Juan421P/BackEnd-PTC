package ptc2025.backend.Models.DTO.EntityType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EntityTypesDTO {
    private String entityTypeID;

    @NotBlank(message = "El campo es obligatorio")
    private String universityID;

    @NotBlank(message = "El campo es obligatorio")
    @Size(max = 60, message = "No debe tener más de 60 caracteres")
    private String entityType;

    @Pattern(regexp = "^[YN]$", message = "Solo puede responder 'Y' o 'N'")
    private Character isAutoCodeEnabled;
}
