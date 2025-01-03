package org.ricardo.examen.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.ricardo.examen.application.port.input.LoginUseCase;
import org.ricardo.examen.infraestructure.config.JwtAuthenticationFilter;
import org.ricardo.examen.infraestructure.input.rest.data.request.AuthenticationRequest;
import org.ricardo.examen.infraestructure.input.rest.data.response.AuthenticationResponse;
import org.ricardo.examen.infraestructure.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationRestAdapter.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationRestAdapterTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginUseCase loginUseCase;

    @MockBean
    private AuthenticationRestAdapter authenticationRestAdapter;

    @MockBean
    private JwtHelper jwtHelper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccessful() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest("username", "password");
        AuthenticationResponse authResponse = new AuthenticationResponse("mock-jwt-token");

        when(loginUseCase.login(authRequest)).thenReturn(authResponse);

        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk());
    }

}