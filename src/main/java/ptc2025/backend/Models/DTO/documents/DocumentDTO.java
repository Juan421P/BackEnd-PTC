package ptc2025.backend.Models.DTO.documents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {

    @NotBlank(message = "El ID del documento es obligatorio")
    private String id;

    @NotBlank(message = "El nombre del documento es obligatorio")
    private String name;

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String type;

    private LocalDate uploadDate;

    @NotBlank(message = "El propietario es obligatorio")
    private String ownerId;

    private Boolean isActive = true;
}
