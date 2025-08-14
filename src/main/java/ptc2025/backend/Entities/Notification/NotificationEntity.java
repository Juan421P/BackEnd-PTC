package ptc2025.backend.Entities.Notification;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "NOTIFICATIONS")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NotificationEntity {
    @Id
    @GenericGenerator(name = "notificationID", strategy = "guid")
    @GeneratedValue(generator = "notificationID")
    @Column(name = "NOTIFICATIONID")
    private String notificationID;
    @Column(name = "USERID")
    private String userID;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "BODY")
    private String body;
    @Column(name = "SENTAT")
    private LocalDate sentAt;
    @Column(name = "READAT")
    private LocalDate readAt;
}
/**NOTIFICATIONID
 USERID
 TITLE
 BODY
 SENTAT
 READAT*/

/**notificationID
 userID not null
 title not null
 body
 sentAt
 readAt*/
