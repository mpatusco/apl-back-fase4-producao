package com.fiap.producao.exceptions.handlers;

import com.fiap.producao.exceptions.entities.ErroAoAtualizarStatusDoPedidoException;
import com.fiap.producao.exceptions.entities.PedidoJaNaProducaoException;
import com.fiap.producao.exceptions.entities.PedidoNaoEncontradoNaProducaoException;
import com.fiap.producao.exceptions.entities.PedidoNaoIncluidoNaProducaoException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static com.fiap.producao.util.ProducaoHelper.gerarItemproducao;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProducaoExceptionHandlerTest {

    private ProducaoExceptionHandler handler = new ProducaoExceptionHandler();

    @Test
    void deveGerarExcecao_QuandoAtualizarStatusDoPedido() {
        var exception = new ErroAoAtualizarStatusDoPedidoException(UUID.randomUUID());
        var req = new MockHttpServletRequest();
        var res = handler.erroAoAtualizarStatusDoPedidoException(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Test
    void deveGerarExcecao_QuandoPedidoJaNaProducao() {
        var exception = new PedidoJaNaProducaoException(UUID.randomUUID());
        var req = new MockHttpServletRequest();
        var res = handler.pedidoJaNaProducaoException(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    void deveGerarExcecao_QuandoPedidoNaoIncluidoNaProducao() {
        var exception = new PedidoNaoIncluidoNaProducaoException(UUID.randomUUID());
        var req = new MockHttpServletRequest();
        var res = handler.pedidoNaoIncluidoNaProducaoException(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Test
    void deveGerarExcecao_QuandoPedidoNaoEncontradoNaProducao() {
        var exception = new PedidoNaoEncontradoNaProducaoException();
        var req = new MockHttpServletRequest();
        var res = handler.pedidoNaoEncontradoNaProducaoException(exception, req);

        assertThat(res).isInstanceOf(ResponseEntity.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
