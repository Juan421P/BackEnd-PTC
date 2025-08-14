package ptc2025.backend.Models.DTO.CodeTokens;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CodeTokensDTO {
    private String codeTokenID;
    @NotNull(message = "Se necesita el Id de la Universidad")
    private  String universityID;
    @NotNull(message = "Ingrese el token") @Size(max = 50, message = "El token no puede ser mayor a 50 caracteres")
    private  String tokenKey;
    @NotNull @Size(max = 150, message = "No puede ingresar una descripcion mayor a 50 caracteres")
    private String description;
    /**
     CODETOKENID
     UNIVERSITYID
     TOKENKEY
     DESCRIPTION

     codeTokenID
     universityID
     tokenKey
     description
     */
}
