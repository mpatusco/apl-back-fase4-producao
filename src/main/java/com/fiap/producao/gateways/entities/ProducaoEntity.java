package com.fiap.producao.gateways.entities;

import com.fiap.producao.entities.ItemProducao;
import com.fiap.producao.utils.enums.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "producao")
public class ProducaoEntity {

    @Id
    @GeneratedValue
    private Long numeroNaProducao;

    @NotNull
    private UUID idPedido;

    @NotNull
    private UUID idCliente;

    public ProducaoEntity(ItemProducao pedidoProducao) {
        this.idPedido = pedidoProducao.getIdPedido();
        this.idCliente = pedidoProducao.getIdCliente();
    }

    public ItemProducao toItemProducao() {
        var pedidoProducao = new ItemProducao();
        pedidoProducao.setNumeroNaProducao(this.numeroNaProducao);
        pedidoProducao.setIdCliente(this.idCliente);
        pedidoProducao.setIdPedido(this.idPedido);
        return pedidoProducao;
    }
}
