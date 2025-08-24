package ptc2025.backend.Entities.SocialService;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ptc2025.backend.Entities.Universities.UniversityEntity;

@Entity
@Table(name = "SOCIALSERVICEPROJECTS")
@Getter @Setter @ToString
@EqualsAndHashCode
public class SocialServiceEntity
{
    @Id
    @GenericGenerator(name = "idSocialService", strategy = "guid")
    @GeneratedValue (generator = "idSocialService")
    @Column(name = "SOCIALSERVICEPROJECTID")
    private String socialServiceProjectID;
    @Column(name = "UNIVERSITYID")
    private String universityID;
    @Column (name = "SOCIALSERVICEPROJECTNAME")
    private String socialServiceProjectName;
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "UNIVERSITYID", referencedColumnName = "UNIVERSITYID")
    private UniversityEntity university;
}
