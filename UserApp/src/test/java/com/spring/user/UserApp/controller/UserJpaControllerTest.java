package com.spring.user.UserApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.user.UserApp.model.Users;
import com.spring.user.UserApp.service.UserJPAService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserJPAController.class)
@ExtendWith(SpringExtension.class)
public class UserJpaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserJPAService userJPAService;

    @Test
    @SneakyThrows
    void shouldReturnAllUsersTest(){
        //arrange
        List<Users> users =
                Arrays.asList(Users.builder().id(1).name("Kundhavai").dob(LocalDate.now().minusYears(10)).build(),
                        Users.builder().id(1).name("Nandhini").dob(LocalDate.now().minusYears(10)).build());

        Mockito.when(userJPAService.retriveAllUsers()).thenReturn(users);

        //act and assert

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jpa/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());


    }

    @Test
    @SneakyThrows
    void shouldReturnAUserTest(){
        Users aUser = Users.builder().id(1).name("Kundhavai").dob(LocalDate.now().minusYears(10)).build();
        Mockito.when(userJPAService.getAUser(anyInt())).thenReturn(aUser);

        //act and assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/jpa/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("user_name").value("Kundhavai"))
                .andExpect(MockMvcResultMatchers.jsonPath("dob").value(String.valueOf(LocalDate.now().minusYears(10))))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void shouldCreateUserTest() {
        Users aUser = Users.builder().id(1).name("Kala").build();
        Mockito.when(userJPAService.addUser(any(Users.class))).thenReturn(aUser);

        //act and assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/jpa/create/user")
                        .content(asJsonString(aUser))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                //.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/json"))
                .andDo(print());


    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
