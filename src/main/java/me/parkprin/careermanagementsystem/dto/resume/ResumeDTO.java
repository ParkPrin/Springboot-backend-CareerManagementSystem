package me.parkprin.careermanagementsystem.dto.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResumeDTO {
    long id;
    String userId;
    long imageId;
    String imageName;
    String imageType;
    String data;
    String resumeName;
    String resumeSummary;
    String career;
    String resumeSalary;

    @Builder
    public ResumeDTO(String userId, long imageId, String imageName, String imageType,
                     String data, String resumeName, String resumeSummary,
                     String career, String resumeSalary) {
        this.userId = userId;
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageType = imageType;
        this.data = data;
        this.resumeName = resumeName;
        this.resumeSummary = resumeSummary;
        this.career = career;
        this.resumeSalary =resumeSalary;

    }
}
