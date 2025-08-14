package ptc2025.backend.Models.DTO.Universities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class UniversityDTO {
    private String universityID;
    @NotBlank (message = "El nombre de la Universidad no puede quedar vacio")
    private String universityName;
    @NotBlank (message = "El nombre del rector no puede quedar vacio")
    private String rector;
    @NotBlank @Pattern(regexp = "^https?://.+", message = "La página web debe empezar con http:// o https://")
    private String webPage;
}
