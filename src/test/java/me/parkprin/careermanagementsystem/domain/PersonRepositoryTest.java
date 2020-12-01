package me.parkprin.careermanagementsystem.domain;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PersonRepository personRepository;

    @After
    public void cleanup() {
        personRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 로그저장_불러오기() {
        String userNicname = "chris";
        String password = "chris%123";
        userRepository.save(User.builder()
                .userNickname(userNicname)
                .password(bCryptPasswordEncoder.encode(password))
                .version(new Long(1))
                .createIp("localhost")
                .lastPasswordChanged(LocalDateTime.now())
                .lastUpdateIp("localhost")
                .passwordExpired(false)
                .withdraw(false)
                .build());

        List<User> userList = userRepository.findAll();

        User user = userList.get(0);
        personRepository.save(Person.builder()
                .user(user).version(new Long(1))
                .email("abccc123@naver.com")
                .cellphone("010-2222-5555").build());

        List<Person> personList = personRepository.findAll();
        Person person = personList.get(0);
        assertThat(person.getEmail()).isEqualTo("abccc123@naver.com");
        assertThat(person.getCellphone()).isEqualTo("010-2222-5555");
    }
}
