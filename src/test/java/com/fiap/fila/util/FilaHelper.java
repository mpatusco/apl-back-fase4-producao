package com.fiap.producao.util;

import com.fiap.producao.entities.ItemProducao;

import java.util.UUID;

public abstract class ProducaoHelper {
    public static ItemProducao gerarItemproducao() {
        return ItemProducao.builder()
                .idProducao(UUID.randomUUID())
                .idCliente(UUID.randomUUID())
                .idPedido(UUID.randomUUID())
                .numeroNaProducao(1L)
                .build();
    }
}
