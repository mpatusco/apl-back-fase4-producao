package com.fiap.producao.interfaces.gateways;

import com.fiap.producao.utils.enums.StatusPedido;

import java.util.UUID;

public interface IPedidoRepositoryPort {
    void atualizarPedido(UUID idPedido, StatusPedido statusPedido) throws Exception;
}
