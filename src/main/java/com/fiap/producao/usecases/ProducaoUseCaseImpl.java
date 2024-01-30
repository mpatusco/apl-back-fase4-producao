package com.fiap.producao.usecases;

import com.fiap.producao.entities.ItemProducao;
import com.fiap.producao.exceptions.entities.ErroAoAtualizarStatusDoPedidoException;
import com.fiap.producao.exceptions.entities.PedidoJaNaProducaoException;
import com.fiap.producao.exceptions.entities.PedidoNaoEncontradoNaProducaoException;
import com.fiap.producao.exceptions.entities.PedidoNaoIncluidoNaProducaoException;
import com.fiap.producao.interfaces.gateways.IProducaoRepositoryPort;
import com.fiap.producao.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.producao.interfaces.usecases.IProducaoUseCasePort;
import com.fiap.producao.utils.enums.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ProducaoUseCaseImpl implements IProducaoUseCasePort {

    private final IProducaoRepositoryPort producaoRepositoryPort;
    private final IPedidoRepositoryPort pedidoRepositoryPort;

    @Override
    public ItemProducao inserirPedidoNaProducao(ItemProducao producao) throws PedidoJaNaProducaoException {
        if (this.obterPedidoNaProducao(producao.getIdPedido()).isPresent())
            throw new PedidoJaNaProducaoException(producao.getIdPedido());

        var itemProducao = producaoRepositoryPort.inserir(producao);

        try {
            this.pedidoRepositoryPort.atualizarPedido(producao.getIdPedido(), StatusPedido.E);
            return itemProducao;
        } catch (Exception e) {
            this.producaoRepositoryPort.removerItemProducao(producao.getIdPedido());
            throw new PedidoNaoIncluidoNaProducaoException(producao.getIdPedido());
        }
    }

    @Override
    public void concluirPedidoNaProducao(UUID idPedido) throws
            PedidoNaoEncontradoNaProducaoException,
            ErroAoAtualizarStatusDoPedidoException {
        var itemProducao = this.obterPedidoNaProducao(idPedido);
        validarPedido(itemProducao);

        try {
            this.pedidoRepositoryPort.atualizarPedido(itemProducao.get().getIdPedido(), StatusPedido.P);
        } catch (Exception e) {
            throw new ErroAoAtualizarStatusDoPedidoException(idPedido);
        }
    }

    @Override
    public void removerPedidoNaProducao(UUID idPedido) {
        validarPedido(this.obterPedidoNaProducao(idPedido));
        producaoRepositoryPort.removerItemProducao(idPedido);

        try {
            this.pedidoRepositoryPort.atualizarPedido(idPedido, StatusPedido.F);
        } catch (Exception e) {
            throw new ErroAoAtualizarStatusDoPedidoException(idPedido);
        }
    }

    @Override
    public Optional<ItemProducao> obterPedidoNaProducao(UUID idPedido) {
        return producaoRepositoryPort.obterPorIdPedido(idPedido);
    }

    @Override
    public List<ItemProducao> obterPedidosNaProducao(int page, int size) {
        return producaoRepositoryPort.obterPedidos(page, size);
    }

    private void validarPedido(Optional<ItemProducao>  itemProducao) {
        if (itemProducao.isEmpty()) throw new PedidoNaoEncontradoNaProducaoException();
    }
}
