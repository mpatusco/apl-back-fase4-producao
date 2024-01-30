package com.fiap.producao.exceptions.entities;

import java.util.UUID;

public class PedidoNaoIncluidoNaProducaoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoIncluidoNaProducaoException(UUID idPedido) {
        super("Ocorreu um erro ao incluir o pedido: " + idPedido + " na producao.");
    }
}
