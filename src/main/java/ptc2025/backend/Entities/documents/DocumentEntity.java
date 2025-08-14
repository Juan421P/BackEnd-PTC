package ptc2025.backend.Entities.documents;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "documents")
public class DocumentEntity {

    @Id
    @Column(name = "DOCUMENTID")
    private String id;

    @Column(name = "DOCUMENTNAME", nullable = false)
    private String name;

    @Column(name = "DOCUMENTTYPE")
    private String type;

    @Column(name = "UPLOADDATE")
    private LocalDate uploadDate;

    @Column(name = "OWNERID")
    private String ownerId;

    @Column(name = "ISACTIVE")
    private Boolean isActive;


}