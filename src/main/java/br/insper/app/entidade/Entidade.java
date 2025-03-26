package br.insper.app.entidade;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Entidade {

    private String nome;
    private String categoria;

    public Entidade(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    public Entidade() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object obj) {
        return this.nome.equals(((Entidade) obj).getNome());
    }
}
