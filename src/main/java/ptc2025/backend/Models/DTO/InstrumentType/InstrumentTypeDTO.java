package ptc2025.backend.Models.DTO.InstrumentType;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InstrumentTypeDTO {
    private String instrumentTypeID;
    @NotBlank (message = "Ingrese el id de la universidad")
    private String universityID;
    @NotBlank(message = "Ingrese el nombre del instrumento")
    private String instrumentTypeName;
}
/**INSTRUMENTTYPEID
 UNIVERSITYID
 INSTRUMENTTYPENAME*/

/**instrumentTypeID
 universityID
 instrumentTypeName*/