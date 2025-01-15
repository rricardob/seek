package org.ricardo.examen.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ricardo.examen.application.port.input.CreateClientUseCase;
import org.ricardo.examen.application.port.input.CreateClientsUsecase;
import org.ricardo.examen.application.port.input.GetClientsInfoUseCase;
import org.ricardo.examen.application.port.input.GetClientsMetricsUseCase;
import org.ricardo.examen.domain.model.Client;
import org.ricardo.examen.domain.model.ClientInfo;
import org.ricardo.examen.domain.model.ClientMetrics;
import org.ricardo.examen.infraestructure.config.JwtAuthenticationFilter;
import org.ricardo.examen.infraestructure.input.rest.data.request.ClientRequestDto;
import org.ricardo.examen.infraestructure.input.rest.data.request.ClientsRequestDto;
import org.ricardo.examen.infraestructure.input.rest.data.response.ClientInfoResponse;
import org.ricardo.examen.infraestructure.input.rest.data.response.ClientMetricsResponse;
import org.ricardo.examen.infraestructure.input.rest.data.response.CreateClientResponse;
import org.ricardo.examen.infraestructure.input.rest.mapper.ClientRestMapper;
import org.ricardo.examen.infraestructure.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClientRestAdapter.class)
class ClientRestAdapterTest {
    @MockBean
    private CreateClientUseCase createClientUseCase;

    @MockBean
    private CreateClientsUsecase createClientsUsecase;

    @MockBean
    private GetClientsMetricsUseCase getClientsMetricsUseCase;

    @MockBean
    private GetClientsInfoUseCase getClientsInfoUseCase;

    @MockBean
    private ClientRestMapper mapper;

    @MockBean
    private ClientRestAdapter clientRestAdapter;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtHelper jwtHelper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    private String jwtToken;

    @BeforeEach
    void setUp() {
        jwtToken = generateToken("testUser", "WRITE");
    }

    @Test
    void testCreateClient() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(clientRestAdapter).build();
        ClientRequestDto requestDto = new ClientRequestDto("Name", "firstname", "lastname", LocalDate.now());
        Client clientToSave = new Client(null, "Name", "firstname", "lastname", 30, LocalDate.now());
        Client savedClient = new Client(1L, "Name", "firstname", "lastname", 30, LocalDate.now());
        CreateClientResponse response = new CreateClientResponse(UUID.randomUUID(), "Client Name", LocalDate.now());

        when(mapper.toModel(requestDto)).thenReturn(clientToSave);
        when(createClientUseCase.create(clientToSave)).thenReturn(savedClient);
        when(mapper.toCreateClientResponse(savedClient)).thenReturn(response);

        mockMvc.perform(post("/api/v1/client/")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

    }

    @Test
    void testCreateClients() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(clientRestAdapter).build();
        ClientsRequestDto requestDto = new ClientsRequestDto(List.of(
                new ClientRequestDto("Client 1", "client1@example.com", "", LocalDate.now()),
                new ClientRequestDto("Client 2", "client1@example.com", "", LocalDate.now())
        ));
        List<Client> clientsToSave = List.of(
                new Client(null, "Client 1", "firstname", "lastname", 30, LocalDate.now()),
                new Client(null, "Client 2", "firstname", "lastname", 30, LocalDate.now())
        );

        when(mapper.toModels(requestDto.getClients())).thenReturn(clientsToSave);

        mockMvc.perform(post("/api/v1/client/batch")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

    }

    @Test
    void testGetClientsMetrics() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(clientRestAdapter).build();
        ClientMetricsResponse response = new ClientMetricsResponse(10, 1000);

        when(getClientsMetricsUseCase.getMetrics()).thenReturn(new ClientMetrics(10, 1000));
        when(mapper.toClientMetricsResponse(any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/client/metrics")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testGetClientsInfo() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(clientRestAdapter).build();
        ClientInfoResponse response = new ClientInfoResponse(List.of(
                new Client(null, "Client 1", "firstname", "lastname", 30, LocalDate.now()),
                new Client(null, "Client 2", "firstname", "lastname", 40, LocalDate.now())
        ), 200);

        when(getClientsInfoUseCase.getClientsInfo()).thenReturn(new ClientInfo());
        when(mapper.toClientInfoResponse(any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/client/info")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}