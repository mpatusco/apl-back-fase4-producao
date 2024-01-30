package com.fiap.producao.gateways;

import com.fiap.producao.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.producao.interfaces.repositories.PedidoRepository;
import com.fiap.producao.utils.enums.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoRepositoryAdapter implements IPedidoRepositoryPort {

    private final PedidoRepository pedidoRepository;

    @Override
    public void atualizarPedido(UUID idPedido, StatusPedido statusPedido) {
        this.pedidoRepository.atualizarPedido(idPedido, statusPedido);
    }
}
