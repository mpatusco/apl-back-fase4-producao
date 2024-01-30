package com.fiap.producao.adapters;


import com.fiap.producao.entities.ItemProducao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProducaoDTO {
    private Long numeroNaProducao;
    private UUID idPedido;
    private UUID idCliente;

    public ProducaoDTO from(ItemProducao producao) {
        return ProducaoDTO.builder()
                .numeroNaProducao(producao.getNumeroNaProducao())
                .idPedido(producao.getIdPedido())
                .idCliente(producao.getIdCliente())
                .build();
    }
}
