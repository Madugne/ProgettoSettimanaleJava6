package chunyinyu.ProgettoSettimanaleJava6.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    @Setter(value = AccessLevel.NONE)
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private String location;
    private int slots;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Event(String title, String description, LocalDate date, String location, int slots, User user) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.slots = slots;
        this.user = user;
    }
}
