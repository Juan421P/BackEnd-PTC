package ptc2025.backend.Models.DTO.StudentDocument;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudentDocumentsDTO {
    private String studentDocumentID;

    @NotBlank(message = "Ingrese el estudiante que ingresa el documento")
    private String studentID;

    @NotBlank(message = "Ingrese el documento solicitado")
    private String documentID;

    @Pattern(regexp = "[YN]", message = "¿Se presento? debe ser 'Y' o 'N'")
    private Character submitted;

    private LocalDate submissionDate;

    @Pattern(regexp = "[YN]", message = "¿Se verifico? debe ser 'Y' o 'N'")
    private Character verified;

}
