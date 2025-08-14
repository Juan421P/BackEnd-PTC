package ptc2025.backend.Entities.PlanComponents;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "EVALUATIONPLANCOMPONENTS")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PlanComponentsEntity {
    @Id
    @GenericGenerator(name = "idInstrument", strategy = "guid")
    @GeneratedValue(generator = "idInstrument")
    @Column(name = "COMPONENTID")
    private String componentID;

    @Column(name = "INSTRUMENTID")
    private String instrumentID;

    @Column(name = "EVALUATIONPLANID")
    private String evaluationPlanID;

    @Column(name = "RUBRIC")
    private String rubric;

    @Column(name = "COMPONENTNAME")
    private String componentName;

    @Column(name = "WEIGHTPERCENTAGE")
    private Double weightPercentage;

    @Column(name = "ORDERINDEX")
    private Integer orderIndex = 1;
}
