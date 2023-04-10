package br.com.ulkiorra.registroplacas.model;

import java.time.LocalDateTime;

public abstract class Client {
    Long id;
    String nome;
    String telefone;
    String email;
    LocalDateTime dateCad;

    public Client() {
    }

    public Client(Long id, String nome, String telefone, String email, LocalDateTime dateCad) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dateCad = dateCad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateCad() {
        return dateCad;
    }

    public void setDateCad(LocalDateTime dateCad) {
        this.dateCad = dateCad;
    }
}
