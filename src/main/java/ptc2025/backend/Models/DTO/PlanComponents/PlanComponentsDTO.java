package ptc2025.backend.Models.DTO.PlanComponents;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PlanComponentsDTO {
    private String componentID;

    @NotNull
    private String instrumentID;

    @NotNull
    private String evaluationPlanID;

    @Size(max = 100)
    private String rubric;

    @NotBlank
    @Size(min = 4, max = 80)
    private String componentName;

    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("100.00")
    private Double weightPercentage;

    private Integer orderIndex = 1;

}
