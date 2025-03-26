package br.insper.app.entidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FerramentasService {

    @Autowired
    private FerramentasRepository repository;

    public Ferramentas save(Ferramentas ferramenta) {
        return repository.save(ferramenta);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<Ferramentas> findAll() {
        return repository.findAll();
    }
}
