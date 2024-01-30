package com.fiap.producao.gateways;

import com.fiap.producao.entities.ItemProducao;
import com.fiap.producao.gateways.entities.ProducaoEntity;
import com.fiap.producao.interfaces.gateways.IProducaoRepositoryPort;
import com.fiap.producao.interfaces.repositories.ProducaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducaoRepositoryAdapter implements IProducaoRepositoryPort {

    @Autowired
    private final ProducaoRepository producaoRepository;

    @Override
    @Transactional
    public ItemProducao inserir(ItemProducao producao) {
        return producaoRepository
                .save(new ProducaoEntity(producao))
                .toItemProducao();
    }

    @Override
    @Transactional
    public void removerItemProducao(UUID idPedido) {
        producaoRepository.deleteByIdPedido(idPedido);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemProducao> obterPorIdPedido(UUID idPedido) {
        var pedidoProducaoEntity = producaoRepository.findByIdPedido(idPedido);
        return pedidoProducaoEntity.map(ProducaoEntity::toItemProducao);
    }

    @Override
    @Transactional
    public ItemProducao atualizarItemNaProducao(ItemProducao itemProducao) {
        return producaoRepository
                .save(new ProducaoEntity(itemProducao))
                .toItemProducao();

    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemProducao> obterPedidos(int page, int size) {
        return producaoRepository.findAll(PageRequest.of(page, size))
                .toList()
                .stream()
                .map(ProducaoEntity::toItemProducao)
                .collect(Collectors.toList());
    }
}
