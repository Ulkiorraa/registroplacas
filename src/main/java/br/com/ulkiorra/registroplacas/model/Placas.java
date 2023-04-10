package br.com.ulkiorra.registroplacas.model;

import java.time.LocalDateTime;

public class Placas {
    Long id;
    String placa;
    Client solicitante;
    Status status;
    String observation;
    LocalDateTime dateEstampagem;
    LocalDateTime dateFinalizacao;


    public Placas() {
    }

    public Placas(Long id, String placa, Client solicitante, Status status, String observation, LocalDateTime dateEstampagem, LocalDateTime dateFinalizacao) {
        this.id = id;
        this.placa = placa;
        this.solicitante = solicitante;
        this.status = status;
        this.observation = observation;
        this.dateEstampagem = dateEstampagem;
        this.dateFinalizacao = dateFinalizacao;
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

    public Client getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Client solicitante) {
        this.solicitante = solicitante;
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

    public LocalDateTime getDateEstampagem() {
        return dateEstampagem;
    }

    public void setDateEstampagem(LocalDateTime dateEstampagem) {
        this.dateEstampagem = dateEstampagem;
    }

    public LocalDateTime getDateFinalizacao() {
        return dateFinalizacao;
    }

    public void setDateFinalizacao(LocalDateTime dateFinalizacao) {
        this.dateFinalizacao = dateFinalizacao;
    }
}
