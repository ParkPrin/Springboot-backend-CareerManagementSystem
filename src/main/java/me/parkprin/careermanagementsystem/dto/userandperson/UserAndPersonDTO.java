package me.parkprin.careermanagementsystem.dto.userandperson;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserAndPersonDTO {
    private String userId;
    private String password;
    private String email;
    private String cellPhone;
    private String nickName;
    private Long version;

    @Builder
    public UserAndPersonDTO(String userId, String password, String email, String cellPhone, String nickName){
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.cellPhone = cellPhone;
        this.nickName = nickName;
        this.version = null;
    }
}
