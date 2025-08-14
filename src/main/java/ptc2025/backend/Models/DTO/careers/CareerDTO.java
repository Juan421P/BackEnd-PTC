package ptc2025.backend.Models.DTO.careers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CareerDTO {

    @NotBlank(message = "El ID de la carrera es obligatorio")
    private String id;

    @NotBlank(message = "El nombre de la carrera es obligatorio")
    private String name;

    private String description;

    @NotBlank(message = "La facultad asociada es obligatoria")
    private String facultyId;

    @NotBlank(message = "El estado de actividad es obligatorio")
    private Boolean isActive = true;

    public CareerDTO() {
    }

    public CareerDTO(String id, String name, String description, String facultyId, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.facultyId = facultyId;
        this.isActive = isActive;
    }
}
