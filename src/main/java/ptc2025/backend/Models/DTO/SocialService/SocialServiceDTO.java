package ptc2025.backend.Models.DTO.SocialService;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString @EqualsAndHashCode
public class SocialServiceDTO {
    private String socialServiceProjectID;
    @NotBlank
    private String universityID;
    @NotBlank
    private String socialServiceProjectName;
    @NotBlank
    private String description;
}
/**SOCIALSERVICEPROJECTID
 UNIVERSITYID
 SOCIALSERVICEPROJECTNAME
 DESCRIPTION*/
/**socialServiceProjectID
 universityID
 socialServiceProjectName
 description
 */