package com.fiap.producao.exceptions.entities;

import java.util.UUID;

public class ErroAoAtualizarStatusDoPedidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ErroAoAtualizarStatusDoPedidoException(UUID idPedido) {
        super("Ocorreu um erro ao atualizar o status do pedido: " + idPedido + ".");
    }
}
