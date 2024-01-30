package com.fiap.producao.interfaces.repositories;

import com.fiap.producao.utils.enums.StatusPedido;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "atualiza-pedido", url = "${pedido.service.url}")
public interface PedidoRepository {

    @PutMapping(value = "/{idPedido}/status/{status}", headers = {"Content-Type: application/json"})
//    @Headers("Content-Type: application/json")
    void atualizarPedido(@PathVariable("idPedido") UUID idPedido, @PathVariable("status")StatusPedido statusPedido);
}