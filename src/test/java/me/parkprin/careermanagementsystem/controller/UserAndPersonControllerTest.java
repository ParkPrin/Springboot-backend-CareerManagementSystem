package me.parkprin.careermanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import me.parkprin.careermanagementsystem.domain.loggedin.LoggedIn;
import me.parkprin.careermanagementsystem.domain.loggedin.LoggedInRepository;
import me.parkprin.careermanagementsystem.domain.person.Person;
import me.parkprin.careermanagementsystem.domain.person.PersonRepository;
import me.parkprin.careermanagementsystem.domain.user.User;
import me.parkprin.careermanagementsystem.domain.user.UserRepository;
import me.parkprin.careermanagementsystem.dto.response.ResponseDTO;
import me.parkprin.careermanagementsystem.dto.userandperson.UserAndPersonDTO;
import me.parkprin.careermanagementsystem.service.userandperson.UserAndPersonService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAndPersonControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LoggedInRepository loggedInRepository;

    @Autowired
    UserAndPersonService userService;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void cleanup() {
        loggedInRepository.deleteAll();
        personRepository.deleteAll();
        userRepository.deleteAll();
    }

    @LocalServerPort
    private int port;

    @Test
    @WithMockUser(username = "spring")
    public void API로_회원가입() throws Exception {
        UserAndPersonDTO userAndPerson = UserAndPersonDTO.builder().
                userId("chris123")
                .password("test")
                .nickName("chris")
                .email("chris@gmail.com")
                .cellPhone("010-1111-2222")
                .build();

        String url = "http://localhost:" + port + "/user/api/join/v1";

        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(userAndPerson);

        mvc.perform(post(url)
                .contentType(MEDIA_TYPE_JSON_UTF8)
                .accept("*/*")
                .content(requestJson))
                .andExpect(status().isOk());
        List<User> userList = userRepository.findAll();
        List<Person> personList = personRepository.findAll();
        List<LoggedIn> loggedInList = loggedInRepository.findAll();
        assertThat(userList.size()).isEqualTo(1);
        assertThat(personList.size()).isEqualTo(1);
        assertThat(loggedInList.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(username = "spring")
    public void API로_로그인() throws Exception {
        UserAndPersonDTO userAndPerson = UserAndPersonDTO.builder().
                userId("chris123")
                .password("test")
                .nickName("chris")
                .email("chris@gmail.com")
                .cellPhone("010-1111-2222")
                .build();

        String url = "http://localhost:" + port + "/user/api/login/v1";

        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(userAndPerson);

        mvc.perform(post(url)
                .contentType(MEDIA_TYPE_JSON_UTF8)
                .accept("*/*")
                .content(requestJson))
                .andExpect(status().isOk());

        MvcResult mvcResult = mvc.perform(post(url)
                .contentType(MEDIA_TYPE_JSON_UTF8)
                .accept("*/*")
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
        ResponseDTO responseDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);
        assertThat(responseDTO.getState()).isEqualTo(200);

    }
}
