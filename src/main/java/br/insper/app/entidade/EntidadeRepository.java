package br.insper.app.entidade;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntidadeRepository extends MongoRepository<Entidade, Integer> {
    Entidade findByNome(String nome);
}
