package org.aviatrip.identityservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IdentityServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testCreateUserWithInvalidData() throws Exception {

        String userRequestJson = new ObjectMapper().writeValueAsString(Map.of(
                "name", "Name",
                "surname", "Surname",
                "email", "somemail@gmail.com",
                "password", "somepass22",
                "role", "undefined"
        ));

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestJson))
                .andExpect(status().isBadRequest())
                .andDo(print());


    }

    @Test
    void testCreateUser() throws Exception {

        String userRequestJson = new ObjectMapper().writeValueAsString(Map.of(
                "name", "Name",
                "surname", "Surname",
                "email", "somemail@gmail.com",
                "password", "somepass22",
                "role", "customer"
        ));

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isCreated())
                .andDo(print());

    }
}
