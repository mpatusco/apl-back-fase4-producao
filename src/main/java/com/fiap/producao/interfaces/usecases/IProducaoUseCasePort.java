package com.fiap.producao.interfaces.usecases;

import com.fiap.producao.entities.ItemProducao;
import com.fiap.producao.exceptions.entities.PedidoJaNaProducaoException;
import com.fiap.producao.exceptions.entities.PedidoNaoEncontradoNaProducaoException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProducaoUseCasePort {

    public ItemProducao inserirPedidoNaProducao(ItemProducao producao) throws PedidoJaNaProducaoException;

    public Optional<ItemProducao> obterPedidoNaProducao(UUID id);

    public void concluirPedidoNaProducao(UUID id);

    public List<ItemProducao> obterPedidosNaProducao(int page, int size);

    public void removerPedidoNaProducao(UUID idPedido);

}
