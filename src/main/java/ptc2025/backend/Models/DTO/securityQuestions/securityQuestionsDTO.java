package ptc2025.backend.Models.DTO.securityQuestions;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class securityQuestionsDTO {
    @NotNull
    private String id;
    @NotNull
    private String universityID;
    @NotNull
    private String question;
}
