package br.insper.app.entidade.controller;

import br.insper.app.entidade.Ferramentas;
import br.insper.app.entidade.FerramentasController;
import br.insper.app.entidade.FerramentasService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class FerramentasControllerTests {

    @InjectMocks
    private FerramentasController ferramentasController;

    @Mock
    private FerramentasService ferramentasService;

    @Mock
    private RestTemplate restTemplate;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ferramentasController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testListarFerramentas() throws Exception {
        List<Ferramentas> lista = Arrays.asList(
                new Ferramentas("Furadeira", "Furadeira elétrica", "Mecânica", "Admin", "admin@example.com"),
                new Ferramentas("Martelo", "Martelo de borracha", "Construção", "Admin", "admin@example.com")
        );
        Mockito.when(ferramentasService.findAll()).thenReturn(lista);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ferramentas"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(lista)));
    }

    @Test
    void testCadastrarFerramenta_Sucesso() throws Exception {
        Ferramentas ferramenta = new Ferramentas("Furadeira", "Furadeira elétrica", "Mecânica", null, null);
        Ferramentas ferramentaSaved = new Ferramentas("Furadeira", "Furadeira elétrica", "Mecânica", "Admin", "admin@example.com");

        ObjectNode adminNode = objectMapper.createObjectNode();
        adminNode.put("nome", "Admin");
        adminNode.put("papel", "ADMIN");
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(adminNode, HttpStatus.OK);

        Mockito.when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenReturn(responseEntity);

        Mockito.when(ferramentasService.save(Mockito.any(Ferramentas.class))).thenReturn(ferramentaSaved);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/ferramentas")
                        .content(objectMapper.writeValueAsString(ferramenta))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("email", "admin@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ferramentaSaved)));
    }

    @Test
    void testCadastrarFerramenta_UserNotFound() throws Exception {
        Ferramentas ferramenta = new Ferramentas("Furadeira", "Furadeira elétrica", "Mecânica", null, null);

        ResponseEntity<JsonNode> notFoundResponse = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenReturn(notFoundResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/ferramentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ferramenta))
                        .header("email", "inexistente@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCadastrarFerramenta_UserForbidden() throws Exception {
        Ferramentas ferramenta = new Ferramentas("Furadeira", "Furadeira elétrica", "Mecânica", null, null);

        ObjectNode userNode = objectMapper.createObjectNode();
        userNode.put("nome", "User");
        userNode.put("papel", "USER");
        ResponseEntity<JsonNode> userResponse = new ResponseEntity<>(userNode, HttpStatus.OK);

        Mockito.when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/ferramentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ferramenta))
                        .header("email", "user@example.com"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testExcluirFerramenta_Sucesso() throws Exception {
        ObjectNode adminNode = objectMapper.createObjectNode();
        adminNode.put("nome", "Admin");
        adminNode.put("papel", "ADMIN");
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(adminNode, HttpStatus.OK);

        Mockito.when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenReturn(responseEntity);

        Mockito.doNothing().when(ferramentasService).delete("123");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/ferramentas/123")
                        .header("email", "admin@example.com"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testExcluirFerramenta_UserNotFound() throws Exception {
        ResponseEntity<JsonNode> notFoundResponse = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenReturn(notFoundResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/ferramentas/123")
                        .header("email", "inexistente@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testExcluirFerramenta_UserForbidden() throws Exception {
        ObjectNode userNode = objectMapper.createObjectNode();
        userNode.put("nome", "User");
        userNode.put("papel", "USER");
        ResponseEntity<JsonNode> userResponse = new ResponseEntity<>(userNode, HttpStatus.OK);

        Mockito.when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/ferramentas/123")
                        .header("email", "user@example.com"))
                .andExpect(status().isForbidden());
    }
}
