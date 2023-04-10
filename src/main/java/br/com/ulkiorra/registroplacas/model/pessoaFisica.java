package br.com.ulkiorra.registroplacas.model;

import java.time.LocalDateTime;

public class pessoaFisica extends Client {
    String cpf;

    public pessoaFisica() {
    }

    public pessoaFisica(Long id, String nome, String telefone, String email, LocalDateTime dateCad, String cpf) {
        super(id, nome, telefone, email, dateCad);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
