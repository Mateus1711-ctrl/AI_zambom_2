package br.insper.app.entidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntidadeService {

    @Autowired
    private EntidadeRepository repository;

    public List<Entidade> listar() {
        return repository.findAll();
    }

    public Entidade salvar(Entidade entidade) {
        return repository.save(entidade);
    }
}
