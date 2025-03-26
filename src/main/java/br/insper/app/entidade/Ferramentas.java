package br.insper.app.entidade;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ferramentas")
public class Ferramentas {

    @Id
    private String id;

    private String nome;
    private String categoria;
    private String descricao;
    private String nomeUsuario;
    private String emailUsuario;

    public Ferramentas(String nome, String descricao, String categoria, String nomeUsuario, String emailUsuario) {
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getEmailUsuario(){
        return emailUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setEmailUsuario(String emailUsuario){
        this.emailUsuario = emailUsuario;
    }

    @Override
    public boolean equals(Object obj) {
        return this.nome.equals(((Ferramentas) obj).getNome());
    }
}
