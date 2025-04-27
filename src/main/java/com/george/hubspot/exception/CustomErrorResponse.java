package com.george.hubspot.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;


public class CustomErrorResponse {
    @Schema(description = "Código de status HTTP", example = "404")
    private int status;
    @Schema(description = "Mensagem descritiva do erro", example = "Não encontrado")
    private String mensagemUsuario;
    @Schema(description = "Mensagem da excecao", example = "stacktrace")
    private Exception excecao;
    @Schema(description = "Data e hora do erro", example = "2025-01-28T12:34:56")
    private Date data;

    public CustomErrorResponse(int status, String mensagemUsuario, Exception excecao) {
        this.status = status;
        this.mensagemUsuario = mensagemUsuario;
        this.data = new Date();
        this.excecao = excecao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagemUsuario() {
        return mensagemUsuario;
    }

    public void setMensagemUsuario(String mensagemUsuario) {
        this.mensagemUsuario = mensagemUsuario;
    }

    public Exception getExcecao() {
        return excecao;
    }

    public void setExcecao(Exception excecao) {
        this.excecao = excecao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}

