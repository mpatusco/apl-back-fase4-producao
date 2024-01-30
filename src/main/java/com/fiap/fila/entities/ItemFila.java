package com.fiap.producao.entities;

import com.fiap.producao.utils.enums.StatusPedido;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemProducao {
    private UUID idProducao;
    private UUID idCliente;
    private UUID idPedido;
    private Long numeroNaProducao;
}