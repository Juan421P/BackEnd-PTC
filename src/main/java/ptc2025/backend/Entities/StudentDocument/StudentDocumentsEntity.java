package ptc2025.backend.Entities.StudentDocument;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "STUDENTDOCUMENTS")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudentDocumentsEntity {
    @Id
    @GenericGenerator(name = "studentDocumentID", strategy = "guid")
    @GeneratedValue(generator = "studentDocumentID")
    @Column(name = "STUDENTDOCUMENTID")
    private String studentDocumentID;

    @Column(name = "STUDENTID")
    private String studentID;

    @Column(name = "DOCUMENTID")
    private String documentID;

    @Column(name = "SUBMITTED")
    private Character submitted;

    @Column(name = "SUBMISSIONDATE")
    private LocalDate submissionDate;

    @Column(name = "VERIFIED")
    private Character verified;
}
