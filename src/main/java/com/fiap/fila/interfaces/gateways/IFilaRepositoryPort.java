package com.fiap.producao.interfaces.gateways;

import com.fiap.producao.entities.ItemProducao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProducaoRepositoryPort {

    ItemProducao inserir(ItemProducao producao);

    void removerItemProducao(UUID idPedido);

    Optional<ItemProducao> obterPorIdPedido(UUID idPedido);

    ItemProducao atualizarItemNaProducao(ItemProducao itemProducao);

    List<ItemProducao> obterPedidos(int page, int size);
}
