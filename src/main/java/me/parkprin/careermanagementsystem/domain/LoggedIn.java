package me.parkprin.careermanagementsystem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class LoggedIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @Column(nullable = false)
    private Long version;

    @Column
    private LocalDateTime dateCreate;

    private String message;

    @Builder
    public LoggedIn(User user, Long version,
                    LocalDateTime dateCreate, String message) {
        this.user = user;
        this.version = version;
        this.dateCreate = dateCreate;
        this.message = message;
    }
}
