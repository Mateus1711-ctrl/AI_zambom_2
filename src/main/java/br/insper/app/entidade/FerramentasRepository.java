package br.insper.app.entidade;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface FerramentasRepository extends MongoRepository<Ferramentas, String> {
}

