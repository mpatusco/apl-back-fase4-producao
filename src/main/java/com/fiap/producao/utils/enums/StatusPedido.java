package com.fiap.producao.utils.enums;

public enum StatusPedido {
    E("Em preparação"),
    P("Pronto"),
    F("Finalizado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
