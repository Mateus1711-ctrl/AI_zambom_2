package br.insper.app.entidade;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/ferramentas")
public class FerramentasController {

    @Autowired
    private FerramentasService ferramentasService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_API_URL = "http://56.124.127.89:8080/api/usuario/";

    private JsonNode validaAdmin(String email) {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(USER_API_URL + email, JsonNode.class);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        JsonNode usuario = response.getBody();
        if (!usuario.has("papel") || !"ADMIN".equalsIgnoreCase(usuario.get("papel").asText())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return usuario;
    }

    @GetMapping
    public List<Ferramentas> listarFerramentas() {
        return ferramentasService.findAll();
    }

    @PostMapping
    public Ferramentas cadastrarFerramentas(@RequestBody Ferramentas ferramentas, @RequestHeader(name = "email") String email) {
        JsonNode usuario = validaAdmin(email);
        ferramentas.setEmailUsuario(email);
        if (usuario.has("nome")) {
            ferramentas.setNomeUsuario(usuario.get("nome").asText());
        }
        return ferramentasService.save(ferramentas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFerramenta(@PathVariable String id, @RequestHeader(name = "email") String email) {
        validaAdmin(email);
        ferramentasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
