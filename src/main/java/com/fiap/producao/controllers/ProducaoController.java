package com.fiap.producao.controllers;

import com.fiap.producao.adapters.ProducaoDTO;
import com.fiap.producao.entities.ItemProducao;
import com.fiap.producao.interfaces.usecases.IProducaoUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producao")
@RequiredArgsConstructor
public class ProducaoController {

    @Autowired
    private final IProducaoUseCasePort producaoUseCasePort;

    @PostMapping("/clientes/{idCliente}/pedidos/{idPedido}")
    public ResponseEntity<ProducaoDTO> inserirPedidoNaProducao(
            @PathVariable(name = "idPedido") UUID idPedido,
            @PathVariable(name = "idCliente") UUID idCliente) {

        var itemProducao = ItemProducao.builder()
                .idPedido(idPedido)
                .idCliente(idCliente)
                .build();
        var producaoDTO = new ProducaoDTO().from(producaoUseCasePort.inserirPedidoNaProducao(itemProducao));
        return ResponseEntity.ok().body(producaoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> concluirPedidoNaProducao(@PathVariable(name = "id") UUID idPedido) {
        producaoUseCasePort.concluirPedidoNaProducao(idPedido);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerPedidoNaProducao(@PathVariable(name = "id") UUID idPedido) {
        producaoUseCasePort.removerPedidoNaProducao(idPedido);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducaoDTO> obterPedidoNaProducao(@PathVariable(name = "id") UUID idPedido) {
        var iTemProducao = producaoUseCasePort.obterPedidoNaProducao(idPedido);

        if (iTemProducao.isPresent()) {
            var producaoDTO = new ProducaoDTO().from(iTemProducao.get());
            return ResponseEntity.ok().body(producaoDTO);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/pedidos")
    public ResponseEntity<List<ProducaoDTO>> buscarPedidosNaProducao(
            @PageableDefault(size = 10, page = 0)
            @SortDefault(sort = "numeroNaProducao", direction = Sort.Direction.ASC)
            Pageable paginacao) {
        var itemProducao = producaoUseCasePort.obterPedidosNaProducao(paginacao.getPageNumber(), paginacao.getPageSize());
        var itensProducaoDTO = itemProducao
                .stream()
                .map(item -> new ProducaoDTO().from(item))
                .collect(Collectors.toList());
        return new ResponseEntity<>(itensProducaoDTO, HttpStatus.OK);
    }

}
