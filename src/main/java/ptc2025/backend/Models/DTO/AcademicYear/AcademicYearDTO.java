package ptc2025.backend.Models.DTO.AcademicYear;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @Setter
@ToString   @EqualsAndHashCode
public class AcademicYearDTO {
    private String academicYearId;

    private String universityId;

    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo no puede ser vacio")
    @Min(1900)
    @Max(2100)
    private Integer year;

    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo no puede ser vacio")
    private LocalDate startDate;

    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo no puede ser vacio")
    private LocalDate endDate;

    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo no puede ser vacio")
    private Integer cycleCount = 2;

    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo no puede ser vacio")
    @Size(min = 1, max = 1, message = "El campo solo puede ser Y(YES), N(NO)")
    @Pattern(regexp = "[YN]", message = "Solo se permite Y (YES), N(NO)" )
    private String allowInterCycle = "N";

    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo no puede ser vacio")
    private Integer defaultInterCycle = 16;
}
