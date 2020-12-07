package me.parkprin.careermanagementsystem.domain;

import me.parkprin.careermanagementsystem.domain.role.Role;
import me.parkprin.careermanagementsystem.domain.role.RoleRepository;
import me.parkprin.careermanagementsystem.domain.rolemappinguser.RoleMappingUser;
import me.parkprin.careermanagementsystem.domain.rolemappinguser.RoleMappingUserRepository;
import me.parkprin.careermanagementsystem.domain.user.User;
import me.parkprin.careermanagementsystem.domain.user.UserRepository;
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
public class RoleMappingUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMappingUserRepository roleMappingUserRepository;

    @After
    public void cleanup(){
        roleMappingUserRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 유저_롤_맵핑_데이터를_등록하고_유저객체로_유저_롤_맵핑_데이터_불러오기(){
        roleRepository.save(
                Role.builder()
                        .roleId("user")
                        .roleName("사용자")
                        .isAdmin(false)
                        .build());

        List<Role> roleList = roleRepository.findAll();
        Role role = roleList.get(0);
        if (userRepository.findAll().size() == 0){
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
        }
        User user = userRepository.selectByUserId("chris123");
        roleMappingUserRepository.save(
                RoleMappingUser.builder()
                        .user(user)
                        .role(role)
                        .build());

        List<RoleMappingUser> roleMappingUserList = roleMappingUserRepository.selectByUserId(user.getId());
        User roleMappingUserGetUser = roleMappingUserList.get(0).getUser();
        Role roleMappingUserGetRole = roleMappingUserList.get(0).getRole();
        assertThat(user.getId()).isEqualTo(roleMappingUserGetUser.getId());
        assertThat(role.getId()).isEqualTo(roleMappingUserGetRole.getId());
    }
}
