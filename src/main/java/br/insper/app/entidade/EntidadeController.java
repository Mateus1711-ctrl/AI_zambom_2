package br.insper.app.entidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entidade")
public class EntidadeController {

    @Autowired
    private EntidadeService service;

    @GetMapping
    public List<Entidade> listar() {
        return service.getEntidades();  // nome correto do método
    }

    @PostMapping
    public Entidade criar(@RequestBody Entidade entidade) {
        return service.saveEntidade(entidade);  // nome correto do método
    }
}
