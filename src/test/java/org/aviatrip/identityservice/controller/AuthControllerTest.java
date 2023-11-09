package org.aviatrip.identityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aviatrip.identityservice.dto.request.auth.UpdateUserPasswordRequest;
import org.aviatrip.identityservice.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @MockBean
    AuthenticationProvider authenticationProvider;

    @Captor
    ArgumentCaptor<UUID> captor;

    @Test
    void updatePassword() throws Exception {

        doNothing().when(authService).updatePassword(anyString(), captor.capture());

        String content = new ObjectMapper().writeValueAsString(new UpdateUserPasswordRequest("5734242fs"));

        String uuid = UUID.randomUUID().toString();

        mockMvc.perform(patch("/auth/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header("Subject", uuid))
                .andDo(print())
                .andExpect(status().isOk());

        verify(authService, times(1)).updatePassword(anyString(), captor.capture());

        assertThat(captor.getValue().toString()).isEqualTo(uuid);
    }

    @Test
    void deleteUser() {
    }
}