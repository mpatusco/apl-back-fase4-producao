package com.fiap.producao.interfaces.repositories;

import com.fiap.producao.gateways.entities.ProducaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProducaoRepository extends JpaRepository<ProducaoEntity, Long> {
    Optional<ProducaoEntity> findByIdPedido(UUID idPedido);

    void deleteByIdPedido(UUID idPedido);
}
