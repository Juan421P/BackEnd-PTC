package ptc2025.backend.Models.DTO.EvaluationPlans;

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
public class EvaluationPlansDTO {
    private String evaluationPlanID;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String courseOfferingID;

    @NotNull(message = "El campo  es obligatorio")
    @NotBlank(message = "El campo  no puede estar vacío")
    @Size(min = 4, max = 80, message = "El  debe tener entre 4 y 80 caracteres")
    private String planName;

    @Size(max = 200, message = "La description no debe exceder los 200 caracteres")
    private String description;

    private LocalDate createdAt;
}
/**evaluationPlanID
 courseOfferingID
 planName
 description
 createdAt*/