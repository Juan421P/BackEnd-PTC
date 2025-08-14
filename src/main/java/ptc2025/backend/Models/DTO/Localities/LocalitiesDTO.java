package ptc2025.backend.Models.DTO.Localities;

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
public class LocalitiesDTO {
    private String localityID;
    @NotBlank
    private String universityID;
    @NotBlank(message = "Debe indicar si es la sede principal")
    @Size(min = 1, max = 1, message = "Debe ser solo un caracter")
    @Pattern(regexp = "[YN]", message = "Solo se permite Y (YES), N(NO)" )
    private Boolean isMainLocality;
    @NotBlank(message = "La direccion no puede estar vacia")
    @Size(max = 500, message = "La direccion no puede ser mayor de 500 caracteres")
    private String address;
    @NotBlank(message = "ingrese un numero de telefono")
    @Size(max = 8, message = "el numero no puede tener mas de 7 numeros")//8 contando el guion
    private String phoneNumber;
}
/** LOCALITYID
 UNIVERSITYID
 ISMAINLOCALITY
 ADDRESS
 PHONENUMBER*/