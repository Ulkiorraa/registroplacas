package br.com.ulkiorra.registroplacas.model;

import java.time.LocalDateTime;

public class pessoaJuridica extends Client {
    String cnpj;

    public pessoaJuridica() {
    }

    public pessoaJuridica(Long id, String nome, String telefone, String email, LocalDateTime dateCad, String cnpj) {
        super(id, nome, telefone, email, dateCad);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
