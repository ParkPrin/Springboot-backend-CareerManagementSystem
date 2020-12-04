package me.parkprin.careermanagementsystem.service;

import me.parkprin.careermanagementsystem.domain.*;
import me.parkprin.careermanagementsystem.dto.UserAndPersonDTO;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.security.auth.login.LoginException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LoggedInRepository loggedInRepository;

    @Autowired
    UserAndPersonService userService;

    @After
    public void cleanup() {
        loggedInRepository.deleteAll();
        personRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 회원가입() {
        String password = "test";
        UserAndPersonDTO userAndPerson = UserAndPersonDTO.builder().
                userId("chris123")
                .password(password)
                .nickName("chris")
                .email("chris@gmail.com")
                .cellPhone("010-1111-2222")
                .build();
        userAndPerson.setVersion(new Long(1));
        assertThat(userService.join(userAndPerson).getPassword())
                .isNotEqualTo(password);
    }

    @Test
    public void 회원가입_후_UserAndPerson_객체로_반환하기() {
        UserAndPersonDTO userAndPerson = UserAndPersonDTO.builder().
                userId("chris123")
                .password("test")
                .nickName("chris")
                .email("chris@gmail.com")
                .cellPhone("010-1111-2222")
                .build();
        userService.join(userAndPerson);
        UserAndPersonDTO userAndPerson1 = userService.selectByUserId("chris123");
        assertThat(userAndPerson.getEmail()).isEqualTo(userAndPerson1.getEmail());
    }

    @Test
    public void 회원가입_후_아이디_이메일로_로그인하기() throws LoginException {
        UserAndPersonDTO userAndPerson = UserAndPersonDTO.builder().
                userId("chris123")
                .password("test")
                .nickName("chris")
                .email("chris@gmail.com")
                .cellPhone("010-1111-2222")
                .build();
        userService.join(userAndPerson);
        assertThat(userService.login(userAndPerson.getUserId(), userAndPerson.getPassword())).isEqualTo("200");
        assertThat(userService.login(userAndPerson.getEmail(), userAndPerson.getPassword())).isEqualTo("200");
        List<LoggedIn> loggedInList = loggedInRepository.findAll();
        assertThat(loggedInList.size()).isEqualTo(3);
    }
}
