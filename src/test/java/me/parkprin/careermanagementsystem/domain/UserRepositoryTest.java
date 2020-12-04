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
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void 계정저장_불러오기() {
        String userId = "chris123";
        String userNicname = "chris";
        String password = "chris%123";
        userRepository.save(User.builder()
                .userId(userId)
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
        assertThat(user.getUserNickname()).isEqualTo(userNicname);
        assertThat(bCryptPasswordEncoder.matches(password, user.getPassword()));
    }

    @Test
    public void 유저아이디로_자동생성된_유저테이블_아이디_불러오기() {
        String userId = "chris123";
        String userNicname = "chris";
        String password = "chris%123";
        userRepository.save(User.builder()
                .userId(userId)
                .userNickname(userNicname)
                .password(bCryptPasswordEncoder.encode(password))
                .version(new Long(1))
                .createIp("localhost")
                .lastPasswordChanged(LocalDateTime.now())
                .lastUpdateIp("localhost")
                .passwordExpired(false)
                .withdraw(false)
                .build());
        User user = userRepository.selectByUserId(userId);
        assertThat(user.getUserId()).isEqualTo(userId);
    }
}
