package me.parkprin.careermanagementsystem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Person extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="userId")
    private User user;

    @Column(nullable = false)
    private Long version;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 13, nullable = true)
    private String cellphone;

    @Column
    private LocalDateTime dateCreated;

    @Column
    private LocalDateTime lastUpdated;

    @Builder
    public Person(User user, Long version, String email,
                  String cellphone) {
        this.user = user;
        this.version = version;
        this.email = email;
        this.cellphone = cellphone;
        this.dateCreated = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }
}
