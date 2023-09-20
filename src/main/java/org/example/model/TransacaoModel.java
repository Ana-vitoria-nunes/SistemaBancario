package org.example.model;

import java.util.Date;

public class TransacaoModel {

    Date dataTransacao;
    Double valorTransacao;
    int conta_Origem;
    int conta_Destino;

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public Double getValorTransacao() {
        return valorTransacao;
    }

    public void setValorTransacao(Double valorTransacao) {
        this.valorTransacao = valorTransacao;
    }

    public int getConta_Origem() {
        return conta_Origem;
    }

    public void setConta_Origem(int conta_Origem) {
        this.conta_Origem = conta_Origem;
    }

    public int getConta_Destino() {
        return conta_Destino;
    }

    public void setConta_Destino(int conta_Destino) {
        this.conta_Destino = conta_Destino;
    }
}
