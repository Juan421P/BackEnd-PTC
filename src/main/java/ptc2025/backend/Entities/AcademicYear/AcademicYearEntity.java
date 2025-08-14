package ptc2025.backend.Entities.AcademicYear;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "ACADEMICYEARS")
@Getter @Setter
@ToString @EqualsAndHashCode
public class AcademicYearEntity {
    @Id
    @GenericGenerator(name = "idAcademicYear", strategy = "guid")
    @GeneratedValue(generator = "idAcademicYear")
    @Column(name = "ACADEMICYEARID")
    private String academicYearId;

    @Column(name = "UNIVERSITYID")
    private String universityId;

    @Column(name = "YEAR")
    @DateTimeFormat
    private Integer year;

    @Column(name = "STARTDATE")
    private LocalDate startDate;

    @Column(name = "ENDDATE")
    private LocalDate endDate;

    @Column(name = "CYCLECOUNT")
    private Integer cycleCount;

    @Column(name = "ALLOWINTERCYCLE")
    private String alowInterCycle;

    @Column(name = "DEFAULTCYCLEDURATION")
    private Integer defaultCycleDuration;

}
