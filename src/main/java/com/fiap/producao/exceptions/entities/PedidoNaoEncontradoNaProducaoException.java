package com.fiap.producao.exceptions.entities;

public class PedidoNaoEncontradoNaProducaoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoNaProducaoException() {
        super("Não foi encontrado um pedido com esse número na producao");
    }
}
