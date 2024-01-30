package com.fiap.producao.controllers;

import com.fiap.producao.entities.ItemProducao;
import com.fiap.producao.interfaces.usecases.IProducaoUseCasePort;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static com.fiap.producao.util.ProducaoHelper.gerarItemproducao;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProducaoControllerTest {

    private MockMvc mockMvc;
    @Mock
    private IProducaoUseCasePort producaoUseCasePort;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ProducaoController producaoController = new ProducaoController(producaoUseCasePort);
        mockMvc = MockMvcBuilders.standaloneSetup(producaoController).addFilter((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve inserir pedido na producao")
    void deveInserirPedidoNaProducao() throws Exception {
        var idPedido = UUID.randomUUID();
        var idCliente = UUID.randomUUID();
        when(producaoUseCasePort.inserirPedidoNaProducao(any(ItemProducao.class))).thenAnswer((i) -> i.getArgument(0));

        mockMvc.perform(post("/producao/clientes/{idCliente}/pedidos/{idPedido}", idCliente, idPedido))
                .andExpect(status().isOk());

        verify(producaoUseCasePort, times(1)).inserirPedidoNaProducao(any(ItemProducao.class));
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve concluir pedido na producao")
    void deveConcluirPedidoNaProducao() throws Exception  {
        var idPedido = UUID.randomUUID();

        mockMvc.perform(put("/producao/{id}", idPedido)).andExpect(status().isOk());

        verify(producaoUseCasePort, times(1)).concluirPedidoNaProducao(any(UUID.class));
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve remover pedido na producao")
    void deveRemoverPedidoNaProducao()  throws Exception  {
        var idPedido = UUID.randomUUID();

        mockMvc.perform(delete("/producao/{id}", idPedido)).andExpect(status().isOk());

        verify(producaoUseCasePort, times(1)).removerPedidoNaProducao(any(UUID.class));
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve obter pedido na producao")
    void deveObterPedidoNaProducao() throws Exception {
        var idPedido = UUID.randomUUID();
        when(producaoUseCasePort.obterPedidoNaProducao(any(UUID.class))).thenReturn(Optional.of(gerarItemproducao()));

        mockMvc.perform(get("/producao/{id}", idPedido))
                .andExpect(status().isOk());

        verify(producaoUseCasePort, times(1)).obterPedidoNaProducao(any(UUID.class));
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve obter os pedidos na producao")
    void deveObterPedidosNaProducao() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        mockMvc.perform(
                get("/producao/pedidos")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))

                )
                .andExpect(status().isOk());

        verify(producaoUseCasePort, times(1)).obterPedidosNaProducao(0, 10);
    }

}
