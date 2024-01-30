package com.fiap.producao.exceptions.entities;

import java.util.UUID;

public class PedidoJaNaProducaoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PedidoJaNaProducaoException(UUID idPedido) {
        super("Pedido com ID: " + idPedido + " já está na producao.");
    }
}
