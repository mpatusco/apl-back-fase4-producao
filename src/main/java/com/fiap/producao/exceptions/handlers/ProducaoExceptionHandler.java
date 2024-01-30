package com.fiap.producao.exceptions.handlers;

import com.fiap.producao.controllers.ProducaoController;
import com.fiap.producao.exceptions.entities.ErroAoAtualizarStatusDoPedidoException;
import com.fiap.producao.exceptions.entities.PedidoJaNaProducaoException;
import com.fiap.producao.exceptions.entities.PedidoNaoEncontradoNaProducaoException;
import com.fiap.producao.exceptions.entities.PedidoNaoIncluidoNaProducaoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {ProducaoController.class})
public class ProducaoExceptionHandler {
    @ExceptionHandler(ErroAoAtualizarStatusDoPedidoException.class)
    public ResponseEntity<StandardError> erroAoAtualizarStatusDoPedidoException(ErroAoAtualizarStatusDoPedidoException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(PedidoJaNaProducaoException.class)
    public ResponseEntity<StandardError> pedidoJaNaProducaoException(PedidoJaNaProducaoException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "O pedido já está na producao", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(PedidoNaoIncluidoNaProducaoException.class)
    public ResponseEntity<StandardError> pedidoNaoIncluidoNaProducaoException(PedidoNaoIncluidoNaProducaoException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(PedidoNaoEncontradoNaProducaoException.class)
    public ResponseEntity<StandardError> pedidoNaoEncontradoNaProducaoException(PedidoNaoEncontradoNaProducaoException e, HttpServletRequest request){
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
