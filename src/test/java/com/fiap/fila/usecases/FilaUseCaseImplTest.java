package com.fiap.producao.usecases;

import com.fiap.producao.entities.ItemProducao;
import com.fiap.producao.exceptions.entities.ErroAoAtualizarStatusDoPedidoException;
import com.fiap.producao.exceptions.entities.PedidoJaNaProducaoException;
import com.fiap.producao.exceptions.entities.PedidoNaoEncontradoNaProducaoException;
import com.fiap.producao.exceptions.entities.PedidoNaoIncluidoNaProducaoException;
import com.fiap.producao.gateways.PedidoRepositoryAdapter;
import com.fiap.producao.interfaces.gateways.IProducaoRepositoryPort;
import com.fiap.producao.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.producao.interfaces.usecases.IProducaoUseCasePort;
import com.fiap.producao.utils.enums.StatusPedido;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static com.fiap.producao.util.ProducaoHelper.gerarItemproducao;

class ProducaoUseCaseImplTest {

    private IProducaoUseCasePort producaoUseCase;
    @Mock
    private IProducaoRepositoryPort producaoRepository;
    @Mock
    private IPedidoRepositoryPort pedidoRepository;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        producaoUseCase = new ProducaoUseCaseImpl(producaoRepository, pedidoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve obter os pedidos na producao")
    void deveObterPedidosNaProducao() {
        var pedidos = producaoUseCase.obterPedidosNaProducao(0, 10);

        assertThat(pedidos).isNotNull();
        verify(producaoRepository, times(1)).obterPedidos(0, 10);
    }

    @Nested
    class InserirPedidoNaProducao {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Inserir pedido na producao")
        void deveInserirPedidoNaProducao() throws Exception {
            var item = gerarItemproducao();
            when(producaoRepository.inserir(any(ItemProducao.class))).thenAnswer((i) -> i.getArgument(0));

            var itemInserido = producaoUseCase.inserirPedidoNaProducao(item);

            assertThat(itemInserido).isNotNull();
            assertThat(itemInserido).isInstanceOf(ItemProducao.class);
            assertThat(itemInserido.getIdPedido()).isEqualTo(item.getIdPedido());
            assertThat(itemInserido.getIdCliente()).isEqualTo(item.getIdCliente());

            verify(producaoRepository, times(1)).inserir(any(ItemProducao.class));
            verify(pedidoRepository, times(1)).atualizarPedido(any(UUID.class), any(StatusPedido.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Gerar uma excecao ao inserir pedido na producao e o pedido ja estiver na producao")
        void deveGerarExcecao_QuandoInserirPedidoNaProducao_PedidoJaNaProducao() throws Exception {
            var item = gerarItemproducao();
            when(producaoUseCase.obterPedidoNaProducao(any(UUID.class))).thenReturn(Optional.of(item));

            assertThatThrownBy(() -> producaoUseCase.inserirPedidoNaProducao(item))
                    .isInstanceOf(PedidoJaNaProducaoException.class)
                    .hasMessage("Pedido com ID: " + item.getIdPedido() + " já está na producao.");

            verify(producaoRepository, never()).inserir(any(ItemProducao.class));
            verify(pedidoRepository, never()).atualizarPedido(any(UUID.class), any(StatusPedido.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Gerar uma excecao ao inserir pedido na producao e o pedido e nao conseguir atualizar status do pedido")
        void deveGerarExcecao_QuandoInserirPedidoNaProducao_PedidoNaoIncluidoNaProducao() throws Exception {
            var item = gerarItemproducao();
            when(producaoRepository.inserir(any(ItemProducao.class))).thenAnswer((i) -> i.getArgument(0));

            doThrow(new Exception()).when(pedidoRepository).atualizarPedido(isA(UUID.class), isA(StatusPedido.class));

            assertThatThrownBy(() -> producaoUseCase.inserirPedidoNaProducao(item))
                    .isInstanceOf(PedidoNaoIncluidoNaProducaoException.class)
                    .hasMessage("Ocorreu um erro ao incluir o pedido: " + item.getIdPedido() + " na producao.");

            verify(producaoRepository, times(1)).inserir(any(ItemProducao.class));
            verify(producaoRepository, times(1)).removerItemProducao(any(UUID.class));
            verify(pedidoRepository, times(1)).atualizarPedido(any(UUID.class), any(StatusPedido.class));
        }
    }

    @Nested
    class ConcluirPedidoNaProducao {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Deve concluir pedido na producao")
        void deveConcluirPedidoNaProducao() throws Exception {
            var item = gerarItemproducao();
            when(producaoUseCase.obterPedidoNaProducao(any(UUID.class))).thenReturn(Optional.of(item));

            producaoUseCase.concluirPedidoNaProducao(item.getIdPedido());

            verify(pedidoRepository, times(1)).atualizarPedido(any(UUID.class), any(StatusPedido.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Deve gerar excecao ao concluir pedido na producao quando pedido nao for encontrado")
        void deveGerarExcecao_QuandoConcluirPedidoNaProducao_PedidoNaoEncontrado() throws Exception {
            var item = gerarItemproducao();
            when(producaoUseCase.obterPedidoNaProducao(any(UUID.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> producaoUseCase.concluirPedidoNaProducao(item.getIdPedido()))
                    .isInstanceOf(PedidoNaoEncontradoNaProducaoException.class)
                    .hasMessage("Não foi encontrado um pedido com esse número na producao");

            verify(pedidoRepository, never()).atualizarPedido(any(UUID.class), any(StatusPedido.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Deve gerar excecao ao concluir pedido na producao quando nao atualizar status do pedido")
        void deveGerarExcecao_QuandoConcluirPedidoNaProducao_StatusDoPedidoNaoAtualizado() throws Exception {
            var item = gerarItemproducao();
            when(producaoUseCase.obterPedidoNaProducao(any(UUID.class))).thenReturn(Optional.of(item));
            doThrow(new Exception()).when(pedidoRepository).atualizarPedido(isA(UUID.class), isA(StatusPedido.class));

            assertThatThrownBy(() -> producaoUseCase.concluirPedidoNaProducao(item.getIdPedido()))
                    .isInstanceOf(ErroAoAtualizarStatusDoPedidoException.class)
                    .hasMessage("Ocorreu um erro ao atualizar o status do pedido: " + item.getIdPedido() + ".");

            verify(pedidoRepository, times(1)).atualizarPedido(any(UUID.class), any(StatusPedido.class));
        }
    }

    @Nested
    class RemoverPedidoNaProducao {
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Deve remover pedido na producao")
        void deveRemoverPedidoNaProducao() throws Exception {
            var item = gerarItemproducao();
            when(producaoUseCase.obterPedidoNaProducao(any(UUID.class))).thenReturn(Optional.of(item));

            producaoUseCase.removerPedidoNaProducao(item.getIdPedido());

            verify(pedidoRepository, times(1)).atualizarPedido(any(UUID.class), any(StatusPedido.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Deve gerar excecao ao remover pedido na producao quando pedido nao for encontrado")
        void deveGerarExcecao_QuandoRemoverPedidoNaProducao_PedidoNaoEncontrado() throws Exception {
            var item = gerarItemproducao();
            when(producaoUseCase.obterPedidoNaProducao(any(UUID.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> producaoUseCase.removerPedidoNaProducao(item.getIdPedido()))
                    .isInstanceOf(PedidoNaoEncontradoNaProducaoException.class)
                    .hasMessage("Não foi encontrado um pedido com esse número na producao");

            verify(pedidoRepository, never()).atualizarPedido(any(UUID.class), any(StatusPedido.class));
        }
        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Deve gerar excecao ao remover pedido na producao quando nao atualizar status do pedido")
        void deveGerarExcecao_QuandoRemoverPedidoNaProducao_StatusDoPedidoNaoAtualizado() throws Exception {
            var item = gerarItemproducao();
            when(producaoUseCase.obterPedidoNaProducao(any(UUID.class))).thenReturn(Optional.of(item));
            doThrow(new Exception()).when(pedidoRepository).atualizarPedido(isA(UUID.class), isA(StatusPedido.class));

            assertThatThrownBy(() -> producaoUseCase.removerPedidoNaProducao(item.getIdPedido()))
                    .isInstanceOf(ErroAoAtualizarStatusDoPedidoException.class)
                    .hasMessage("Ocorreu um erro ao atualizar o status do pedido: " + item.getIdPedido() + ".");

            verify(pedidoRepository, times(1)).atualizarPedido(any(UUID.class), any(StatusPedido.class));
        }
    }
}
