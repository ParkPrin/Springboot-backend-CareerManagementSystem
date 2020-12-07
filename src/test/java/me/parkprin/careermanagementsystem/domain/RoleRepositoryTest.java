package me.parkprin.careermanagementsystem.domain;
import me.parkprin.careermanagementsystem.domain.role.Role;
import me.parkprin.careermanagementsystem.domain.role.RoleRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @After
    public void cleanup(){
        roleRepository.deleteAll();
    }

    @Test
    public void role등록(){
        roleRepository.save(
                Role.builder()
                        .roleId("user")
                        .roleName("사용자")
                        .isAdmin(false)
                        .build());

        List<Role> roleList = roleRepository.findAll();
        Role role = roleList.get(0);
        assertThat(role.getRoleId()).isEqualTo("user");
    }

    @Test
    public void role을_roleId로_검색(){
        roleRepository.save(
                Role.builder()
                        .roleId("user")
                        .roleName("사용자")
                        .isAdmin(false)
                        .build());

        List<Role> roleList = roleRepository.findAll();
        Role role = roleList.get(0);
        Role selectByRole = roleRepository.selectByRoleId("user");
        assertThat(role.getRoleId()).isEqualTo(selectByRole.getRoleId());
    }
}
