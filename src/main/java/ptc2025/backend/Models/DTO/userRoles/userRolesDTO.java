package ptc2025.backend.Models.DTO.userRoles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class userRolesDTO {

    private String userRoleid;
    @NotBlank (message = "No puede dejar el usuario vacio")
    private String Userid;
    @NotBlank(message = "Por favor ingrese el tipo de rol de usuario")
    private String roleType;

}
