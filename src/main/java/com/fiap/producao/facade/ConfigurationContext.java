package com.fiap.producao.facade;

import com.fiap.producao.interfaces.gateways.IProducaoRepositoryPort;
import com.fiap.producao.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.producao.interfaces.usecases.IProducaoUseCasePort;
import com.fiap.producao.usecases.ProducaoUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationContext {
    @Bean
    public IProducaoUseCasePort iproducaoUseCasePort(IProducaoRepositoryPort producaoRepositoryPort, IPedidoRepositoryPort pedidoRepositoryPort) {
        return new ProducaoUseCaseImpl(producaoRepositoryPort, pedidoRepositoryPort);
    }

}
