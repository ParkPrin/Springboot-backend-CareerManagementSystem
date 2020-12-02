package me.parkprin.careermanagementsystem.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserAndPerson {
    private String userId;
    private String password;
    private String email;
    private String cellPhone;
    private String nickName;

    @Builder
    public UserAndPerson(String userId, String password, String email, String cellPhone, String nickName){
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.cellPhone = cellPhone;
        this.nickName = nickName;
    }

    public UserAndPerson(User user){
        this.userId = user.getUserId();
        this.nickName = user.getUserNickname();
    }

    public UserAndPerson setPerson(Person person){
        this.email = person.getEmail();
        this.cellPhone = person.getCellphone();
        return this;
    }
}
