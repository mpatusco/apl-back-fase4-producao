package com.fiap.producao.gateways;

import com.fiap.producao.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.producao.interfaces.repositories.PedidoRepository;
import com.fiap.producao.utils.enums.StatusPedido;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static com.fiap.producao.util.ProducaoHelper.gerarItemproducao;

class PedidoRepositoryTest {

    private IPedidoRepositoryPort pedidoRepository;
    @Mock
    private PedidoRepository repository;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        pedidoRepository = new PedidoRepositoryAdapter(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve atualizar status do pedido")
    void deveAtualizarPedido() throws Exception {
        StatusPedido.P.getDescricao();
        pedidoRepository.atualizarPedido(UUID.randomUUID(), StatusPedido.E);
        verify(repository, times(1)).atualizarPedido(any(UUID.class), any(StatusPedido.class));
    }

}
