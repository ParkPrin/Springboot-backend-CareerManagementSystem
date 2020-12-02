package me.parkprin.careermanagementsystem.service;

import me.parkprin.careermanagementsystem.domain.LoggedInRepository;
import me.parkprin.careermanagementsystem.domain.PersonRepository;
import me.parkprin.careermanagementsystem.domain.UserAndPerson;
import me.parkprin.careermanagementsystem.domain.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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
    UserService userService;

    @After
    public void cleanup() {
        loggedInRepository.deleteAll();
        personRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 회원가입() {
        UserAndPerson userAndPerson = UserAndPerson.builder().
                userId("chris123")
                .password("test")
                .nickName("chris")
                .email("chris@gmail.com")
                .cellPhone("010-1111-2222")
                .build();
        assertThat(userRepository.findById(userService.join(userAndPerson)).get().getUserId())
                .isEqualTo(userAndPerson.getUserId());
    }

    @Test
    public void 회원가입_후_UserAndPerson_객체로_반환하기() {
        UserAndPerson userAndPerson = UserAndPerson.builder().
                userId("chris123")
                .password("test")
                .nickName("chris")
                .email("chris@gmail.com")
                .cellPhone("010-1111-2222")
                .build();
        userService.join(userAndPerson);
        UserAndPerson userAndPerson1 = userService.selectByUserId("chris123");
        assertThat(userAndPerson.getEmail()).isEqualTo(userAndPerson1.getEmail());
    }
}
