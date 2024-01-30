package com.fiap.producao.facade;

import com.fiap.producao.interfaces.gateways.IProducaoRepositoryPort;
import com.fiap.producao.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.producao.interfaces.usecases.IProducaoUseCasePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ConfigurationContextTest {

    private ConfigurationContext config = new ConfigurationContext();
    @Mock
    private IProducaoRepositoryPort producaoRepositoryPort;
    @Mock
    private IPedidoRepositoryPort pedidoRepositoryPort;
    AutoCloseable mock;
    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void deveConfigurarProducaoUseCase() {
        var producaoUseCase = config.iproducaoUseCasePort(producaoRepositoryPort, pedidoRepositoryPort);

        assertThat(producaoUseCase).isNotNull();
        assertThat(producaoUseCase).isInstanceOf(IProducaoUseCasePort.class);
    }

}
