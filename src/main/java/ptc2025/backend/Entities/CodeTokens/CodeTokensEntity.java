package ptc2025.backend.Entities.CodeTokens;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CODETOKENS")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CodeTokensEntity {
    @Id
    @GenericGenerator(name = "idCodeToken", strategy = "guid")
    @GeneratedValue(generator = "idCodeToken")
    @Column(name = "CODETOKENID")
    private String codeTokenID;
    @Column(name = "UNIVERSITYID")
    private  String universityID;
    @Column(name = "TOKENKEY")
    private  String tokenKey;
    @Column(name = "DESCRIPTION")
    private String description;
    /**
     CODETOKENID
     UNIVERSITYID
     TOKENKEY
     DESCRIPTION

     codeTokenID
     universityID
     tokenKey
     description
     */
}
