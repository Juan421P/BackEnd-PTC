package ptc2025.backend.Entities.DataAuditLog;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "DATAAUDITLOG")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DataAuditLogEntity {
    @Id
    @GenericGenerator(name = "auditLogID", strategy = "guid")
    @GeneratedValue(generator = "auditLogID")
    @Column(name = "AUDITLOGID")
    private String auditLogID;

    @Column(name = "USERID")
    private String userID;

    @Column(name = "AFFECTEDTABLE")
    private String affectedTable;

    @Column(name = "RECORDID")
    private String recordID;

    @Column(name = "OPERATIONTYPE")
    private String operationType;

    @Column(name = "OPERATIONAT")
    private LocalDate operationAt;

    @Column(name = "OLDVALUES", columnDefinition = "CLOB")
    private String oldValues;

    @Column(name = "NEWVALUES", columnDefinition = "CLOB")
    private String newValues;


}
