package me.parkprin.careermanagementsystem.service.userandperson;

import me.parkprin.careermanagementsystem.domain.loggedin.LoggedIn;
import me.parkprin.careermanagementsystem.domain.loggedin.LoggedInRepository;
import me.parkprin.careermanagementsystem.domain.person.Person;
import me.parkprin.careermanagementsystem.domain.person.PersonRepository;
import me.parkprin.careermanagementsystem.domain.role.Role;
import me.parkprin.careermanagementsystem.domain.role.RoleRepository;
import me.parkprin.careermanagementsystem.domain.rolemappinguser.RoleMappingUser;
import me.parkprin.careermanagementsystem.domain.rolemappinguser.RoleMappingUserRepository;
import me.parkprin.careermanagementsystem.domain.user.User;
import me.parkprin.careermanagementsystem.domain.user.UserRepository;
import me.parkprin.careermanagementsystem.dto.userandperson.UserAndPersonDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;

@Service
public class UserAndPersonService {
    private static final Long VERSION = new Long(1);

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMappingUserRepository roleMappingUserRepository;

    @Autowired
    LoggedInRepository loggedInRepository;

    /**
     *
     * @param userId
     * @param isUseException
     * @return UserAndPerson
     *
     * 유저 아이디로 User와 Person 테이블 안에 있는 필수정보를 UserAndPerson 도메인에 맵핑하여 전달하는 메소드
     */

    public UserAndPersonDTO selectByUserId(String userId, boolean isUseException){
        User user = null;
        Person person = null;
        try {
            user = userRepository.selectByUserId(userId);
            person = personRepository.selectByUserId(user.getId());
        } catch (IllegalArgumentException e){
            if(isUseException) throw new IllegalArgumentException("해당 계정은 존재하지 않습니다. id=" + userId);
        } catch (NullPointerException e){
            if(isUseException) throw new IllegalArgumentException("해당 계정은 존재하지 않습니다. id=" + userId);
        }
        if (user == null) return null;
        else return UserAndPersonDTO.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .nickName(user.getUserNickname())
                .email(person.getEmail())
                .cellPhone(person.getCellphone())
                .build();
    }

    /**
     *
     * @param userId
     * @return
     * 위 설명과 동일함 단 isUseException의 값이 true로 default된 상태
     */

    public UserAndPersonDTO selectByUserId(String userId) {
        return selectByUserId(userId, true);
    }

    /**
     *
     * @param userIdAndEmail
     * @param password
     * @return
     * @throws LoginException
     * userId와 email을 아이디로 하여 패스워드와 같이 로그인을 검증하는 기능
     */

    public String login(String userIdAndEmail, String password) throws LoginException {
        String loginCode = "";
        UserAndPersonDTO userAndPerson = selectByUserId(userIdAndEmail, false);
        if (userAndPerson != null){
            loginCode = passwordCheck(userAndPerson, password);
            if(StringUtils.isNotEmpty(loginCode)) writeSystemMessageLog(userIdAndEmail, "[" + userIdAndEmail + "] 로그인 되었습니다");
        } else {
            Person person = null;
            try {
                person = personRepository.selectByEmail(userIdAndEmail);
                userAndPerson = selectByUserId(person.getUser().getUserId());
                loginCode = passwordCheck(userAndPerson, password);
                if(StringUtils.isNotEmpty(loginCode)) writeSystemMessageLog(userIdAndEmail, "[" + userIdAndEmail + "] 로그인 되었습니다");
            } catch (NullPointerException e) {
                throw new NullPointerException("아이디와 이메일에 부합하는 계정이 존재하지 않습니다");
            }
        }
        if (StringUtils.isEmpty(loginCode)) loginCode = "400";
        return loginCode;
    }

    /**
     *
     * @param userAndPerson
     * @param password
     * @return
     * @throws LoginException
     * 인코딩된 패스워드와 문자적 패스워드가 일치하는지 검증하는 기능
     */

    private String passwordCheck(UserAndPersonDTO userAndPerson, String password) throws LoginException{
        if (userAndPerson != null){
            if (bCryptPasswordEncoder.matches(password, userAndPerson.getPassword())) return "200";
            else throw new LoginException("로그인 인증을 실패하였습니다. 비밀번호를 확인하세요");
        } else throw new NullPointerException("아이디와 이메일에 부합하는 계정이 존재하지 않습니다");
    }

    /**
     *
     * @param userAndPerson
     * @return
     * 회원가입하는 기능
     */


    public UserAndPersonDTO join(UserAndPersonDTO userAndPerson){
        try {
            User user = userRepository.save(User.builder()
                    .userId(userAndPerson.getUserId())
                    .userNickname(userAndPerson.getNickName())
                    .password(bCryptPasswordEncoder.encode(userAndPerson.getPassword()))
                    .version(userAndPerson.getVersion())
                    .createIp("localhost")
                    .lastPasswordChanged(LocalDateTime.now())
                    .lastUpdateIp("localhost")
                    .passwordExpired(false)
                    .withdraw(false)
                    .build());
            personRepository.save(Person.builder()
                    .user(user).version(userAndPerson.getVersion())
                    .email(userAndPerson.getEmail())
                    .cellphone(userAndPerson.getCellPhone()).build());

            if (roleRepository.findAll().size() == 0 ){
                roleRepository.save(
                        Role.builder()
                                .roleId("user")
                                .roleName("사용자")
                                .isAdmin(false)
                                .build());
            }
            roleMappingUserRepository.save(
                    RoleMappingUser.builder()
                            .user(user)
                            .role(roleRepository.selectByRoleId("user"))
                            .build());

            loggedInRepository.save(LoggedIn.builder()
                    .user(user)
                    .version(userAndPerson.getVersion())
                    .dateCreate(LocalDateTime.now())
                    .message("메세지 코드 - 회원 가입자: " + user.getUserId() )
                    .build());

            userAndPerson.setPassword(userRepository.selectByUserId(userAndPerson.getUserId()).getPassword());
        } catch (Exception e){

        }
        return userAndPerson;
    }

    /**
     *
     * @param userIdAndEmail
     * @param setMessage
     * 시스템 이벤트 발생시 로그를 남길 때 첫번째 파라미터로 계정 아이디, 이메일 두 번째 파라미터로 로그메시지를 주입하여 시스템 로그를 남긴다.
     */

    private void writeSystemMessageLog(String userIdAndEmail, String setMessage){
        UserAndPersonDTO userAndPerson = selectByUserId(userIdAndEmail, false);
        if (userAndPerson == null){
            Person person = personRepository.selectByEmail(userIdAndEmail);
            userAndPerson = selectByUserId(person.getUser().getUserId());
        }
        User user = userRepository.selectByUserId(userAndPerson.getUserId());
        loggedInRepository.save(LoggedIn.builder()
                .user(user)
                .version(new Long(1))
                .dateCreate(LocalDateTime.now())
                .message(setMessage)
                .build());
    }

}
