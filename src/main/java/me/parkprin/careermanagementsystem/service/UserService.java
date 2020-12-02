package me.parkprin.careermanagementsystem.service;

import me.parkprin.careermanagementsystem.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LoggedInRepository loggedInRepository;

    public UserAndPerson selectByUserId(String userId) {
        User user = null;
        Person person = null;
        try {
            user = userRepository.selectByUserId(userId);
            person = personRepository.selectByUserId(user.getId());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("해당 계정은 존재하지 않습니다. id=" + userId);
        }
        return new UserAndPerson(user).setPerson(person);

    }

    /*
    private String userId;
    private String password;
    private String email;
    private String cellPhone;
    private String nickName;
     */

    public Long join(UserAndPerson userAndPerson){

        User user = userRepository.save(User.builder()
                .userId(userAndPerson.getUserId())
                .userNickname(userAndPerson.getNickName())
                .password(bCryptPasswordEncoder.encode(userAndPerson.getPassword()))
                .version(new Long(1))
                .createIp("localhost")
                .lastPasswordChanged(LocalDateTime.now())
                .lastUpdateIp("localhost")
                .passwordExpired(false)
                .withdraw(false)
                .build());
        personRepository.save(Person.builder()
                .user(user).version(new Long(1))
                .email(userAndPerson.getEmail())
                .cellphone(userAndPerson.getCellPhone()).build());

        loggedInRepository.save(LoggedIn.builder()
                .user(user)
                .version(new Long(1))
                .dateCreate(LocalDateTime.now())
                .message("메세지 코드 - 회원 가입자: " + user.getUserId() )
                .build());
        return user.getId();



    }

}
