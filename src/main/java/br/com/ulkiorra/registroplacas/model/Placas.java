package br.com.ulkiorra.registroplacas.model;

import java.time.LocalDate;

public class Placas {
    Long id;
    String placa;
    String telefone;
    String nome;
    Status status;
    String observation;
    String vendedor;
    LocalDate dateEstampagem;
    LocalDate dateFinalizacao;


    public Placas() {
    }

    public Placas(Long id, String placa, String telefone, String nome, Status status, String observation, String vendedor, LocalDate dateEstampagem, LocalDate dateFinalizacao) {
        this.id = id;
        this.placa = placa;
        this.telefone = telefone;
        this.nome = nome;
        this.status = status;
        this.observation = observation;
        this.vendedor = vendedor;
        this.dateEstampagem = dateEstampagem;
        this.dateFinalizacao = dateFinalizacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDate getDateEstampagem() {
        return dateEstampagem;
    }

    public void setDateEstampagem(LocalDate dateEstampagem) {
        this.dateEstampagem = dateEstampagem;
    }

    public LocalDate getDateFinalizacao() {
        return dateFinalizacao;
    }

    public void setDateFinalizacao(LocalDate dateFinalizacao) {
        this.dateFinalizacao = dateFinalizacao;
    }
}
