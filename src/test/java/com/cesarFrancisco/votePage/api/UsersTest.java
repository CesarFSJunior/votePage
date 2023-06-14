package com.cesarFrancisco.votePage.api;

import com.cesarFrancisco.votePage.api.insertDto.UserInsertDto;
import com.cesarFrancisco.votePage.domain.entities.User;
import com.cesarFrancisco.votePage.domain.repositories.UserRepository;
import com.cesarFrancisco.votePage.domain.repositories.VoteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class UsersTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    ObjectMapper mapper;

    String authHeader;

    UserInsertDto genericInsertDto = new UserInsertDto("Maria", "maria@hotmail.com", "12345", null);

    Long genericUserId;

    @BeforeEach
    public void createUser() throws Exception {

        voteRepository.deleteAll();
        userRepository.deleteAll();

        MvcResult registerRes = mockMvc.perform(MockMvcRequestBuilders.post("/users").content(mapper.writeValueAsString(genericInsertDto)).contentType(MediaType.APPLICATION_JSON)).andReturn();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/login").content(mapper.writeValueAsString(genericInsertDto)).contentType(MediaType.APPLICATION_JSON)).andReturn();

        String token = result.getResponse().getContentAsString().split("\"")[3];

        genericUserId = mapper.readValue(registerRes.getResponse().getContentAsString(), User.class).getId();

        authHeader = "Bearer " + token;
    }

    @AfterEach
    public void clearDb() {
        userRepository.deleteAll();
        voteRepository.deleteAll();
    }


    @Test
    @DisplayName("It should Get all users")
    public void getAllUsers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users").header("Authorization", authHeader))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("It Should create a new user")
    public void insertUser() throws Exception {

        UserInsertDto userInsertDto = new UserInsertDto("Alex", "alex@gmail.com", "54321", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(mapper.writeValueAsString(userInsertDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("It should perform a login")
    public void login() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .content(mapper.writeValueAsString(genericInsertDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("It should get a user by id")
    public void getById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + genericUserId)
                .header("Authorization", authHeader))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("It should update a user")
    public void updateUser() throws Exception {

        UserInsertDto updateDto = new UserInsertDto("Maria2", "maria2@gmail.com",null, null);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/" + genericUserId)
                .content(mapper.writeValueAsString(updateDto))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authHeader))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("It should delete the user")
    public void deleteUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + genericUserId)
                .header("Authorization", authHeader))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("It should return a error when a request without a valid authorization be performed")
    public void noAuthRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("It should not allow a user to delete another person account")
    public void deleteAnotherPersonAccount() throws Exception {

        UserInsertDto secondUser = new UserInsertDto("Andre", "andre@hotmail.com", "9876", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secondUser)))
                .andReturn();

        MvcResult secondUserTokenResponse = mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .content(mapper.writeValueAsString(secondUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        String secondUserToken = secondUserTokenResponse.getResponse().getContentAsString().split("\"")[3];


        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + genericUserId)
                .header("Authorization", "Bearer " + secondUserToken))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("It should not allow a user to update another person account")
    public void updateAnotherPersonAccount() throws Exception {

        UserInsertDto secondUser = new UserInsertDto("Andre", "andre@hotmail.com", "9876", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(secondUser)))
                .andReturn();

        MvcResult secondUserTokenResponse = mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                        .content(mapper.writeValueAsString(secondUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        String secondUserToken = secondUserTokenResponse.getResponse().getContentAsString().split("\"")[3];

        UserInsertDto secondUserUpdateDto = new UserInsertDto("Andre2", "andre2@hotmail.com", null, null);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/" + genericUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secondUserUpdateDto))
                .header("Authorization", "Bearer " + secondUserToken))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("Should not create a user without a Name")
    public void createAUserWithoutEmail() throws Exception {

        UserInsertDto userInsertDto = new UserInsertDto(null, "mario@gmail.com", "123124", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(mapper.writeValueAsString(userInsertDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("Should not create a user without a email")
    public void createAUserWithoutName() throws Exception {

        UserInsertDto userInsertDto = new UserInsertDto("mario", null, "123124", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(mapper.writeValueAsString(userInsertDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("Should not create a user without a password")
    public void createAUserWithoutPassword() throws Exception {

        UserInsertDto userInsertDto = new UserInsertDto("mario", "mario@gmail.com", null, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(mapper.writeValueAsString(userInsertDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }
}
