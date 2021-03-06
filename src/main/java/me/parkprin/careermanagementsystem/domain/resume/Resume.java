package me.parkprin.careermanagementsystem.domain.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.parkprin.careermanagementsystem.domain.BaseTimeEntity;
import me.parkprin.careermanagementsystem.domain.image.Image;
import me.parkprin.careermanagementsystem.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Resume extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @OneToOne
    @JoinColumn(name="imageId")
    private Image image;

    @Column
    private String resumeName;

    @Column(length = 150, nullable = false)
    private String career;

    @Column(length = 150, nullable = false)
    private String resumeSalary;

    @Column(length = 255, nullable = false)
    private String resumeSummary;

    @Column
    private LocalDateTime dateCreated;

    @Column
    private LocalDateTime lastUpdated;

    @Builder
    public Resume (User user, Image image, String resumeName,
                   String career, String resumeSalary, String resumeSummary,
                    LocalDateTime dateCreated, LocalDateTime lastUpdated) {
        this.user = user;
        this.image = image;
        this.resumeName = resumeName;
        this.career = career;
        this.resumeSalary = resumeSalary;
        this.resumeSummary = resumeSummary;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }
}
