package br.com.ulkiorra.registroplacas.model;

import java.time.LocalDate;

public class Client {
    Long id;
    String nome;
    String cpf;
    String cnpj;
    String telefone;
    String email;
    LocalDate dateCad;

    public Client() {
    }

    public Client(Long id, String nome, String cpf, String cnpj, String telefone, String email, LocalDate dateCad) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.dateCad = dateCad;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public LocalDate getDateCad() {
        return dateCad;
    }

    public void setDateCad(LocalDate dateCad) {
        this.dateCad = dateCad;
    }
}
