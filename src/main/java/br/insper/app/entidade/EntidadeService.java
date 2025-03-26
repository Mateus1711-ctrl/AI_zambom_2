package br.insper.app.entidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EntidadeService {

    @Autowired
    private EntidadeRepository entidadeRepository;

    public List<Entidade> getEntidades() {
        return entidadeRepository.findAll();
    }

    public Entidade saveEntidade(Entidade entidade) {
        if (entidade.getNome() == null || entidade.getCategoria() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return entidadeRepository.save(entidade);
    }

    public Entidade findEntidadeByNome(String nome) {
        Entidade entidade = entidadeRepository.findByNome(nome);
        if (entidade == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return entidade;
    }

    public void deleteEntidade(String nome) {
        Entidade entidade = findEntidadeByNome(nome);
        entidadeRepository.delete(entidade);
    }

    public CountEntidadeDTO countEntidades() {
        Long count = entidadeRepository.count();
        return new CountEntidadeDTO(count);
    }
}
