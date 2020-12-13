package me.parkprin.careermanagementsystem.domain.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String imageName;

    @Column(length = 150)
    private String imageType;

    @Lob
    private byte[] data;

    @Builder
    public Image(String imageName, String imageType, byte[] data){
        this.imageName = imageName;
        this.imageType = imageType;
        this.data = data;
    }
}
