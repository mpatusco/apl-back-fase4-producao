package com.fiap.producao.gateways;

import com.fiap.producao.entities.ItemProducao;
import com.fiap.producao.gateways.entities.ProducaoEntity;
import com.fiap.producao.interfaces.gateways.IProducaoRepositoryPort;
import com.fiap.producao.interfaces.repositories.ProducaoRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static com.fiap.producao.util.ProducaoHelper.gerarItemproducao;

class ProducaoRepositoryAdapterTest {

    private IProducaoRepositoryPort producaoRepository;
    @Mock
    private ProducaoRepository repository;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        producaoRepository = new ProducaoRepositoryAdapter(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve inserir ItemProducao no repository")
    void deveInserir() {
        var item = gerarItemproducao();
        when(repository.save(any(ProducaoEntity.class))).thenAnswer((i) -> i.getArgument(0));

        var itemInserido = producaoRepository.inserir(item);

        assertThat(itemInserido).isNotNull();
        assertThat(itemInserido).isInstanceOf(ItemProducao.class);
        assertThat(itemInserido.getIdCliente()).isEqualTo(item.getIdCliente());
        assertThat(itemInserido.getIdPedido()).isEqualTo(item.getIdPedido());
        verify(repository, times(1)).save(any(ProducaoEntity.class));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve remover ItemProducao no repository")
    void deveRemover() {
        producaoRepository.removerItemProducao(UUID.randomUUID());

        verify(repository, times(1)).deleteByIdPedido(any(UUID.class));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve obter pedido na producao utilizando id do peiddo")
    void deveObterPorIdPedido() {
        var item = gerarItemproducao();
        when(repository.findByIdPedido(any(UUID.class))).thenReturn(Optional.of(new ProducaoEntity(item)));

        var itemObtido = producaoRepository.obterPorIdPedido(item.getIdPedido());

        assertThat(itemObtido).isNotNull();
        assertThat(itemObtido).isPresent();
        assertThat(itemObtido.get().getIdCliente()).isEqualTo(item.getIdCliente());
        assertThat(itemObtido.get().getIdPedido()).isEqualTo(item.getIdPedido());

        verify(repository, times(1)).findByIdPedido(any(UUID.class));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve atualizar ItemProducao no repository")
    void deveAtualizarItemNaProducao() {
        var item = gerarItemproducao();
        when(repository.save(any(ProducaoEntity.class))).thenAnswer((i) -> i.getArgument(0));

        var itemAtualizado = producaoRepository.atualizarItemNaProducao(item);

        assertThat(itemAtualizado).isNotNull();
        assertThat(itemAtualizado).isInstanceOf(ItemProducao.class);
        assertThat(itemAtualizado.getIdCliente()).isEqualTo(item.getIdCliente());
        assertThat(itemAtualizado.getIdPedido()).isEqualTo(item.getIdPedido());
        verify(repository, times(1)).save(any(ProducaoEntity.class));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deve obter itens na producao")
    void deveObterPedidos() {
        var item01 = gerarItemproducao();
        var testConstructor = new ProducaoEntity(item01.getNumeroNaProducao(), item01.getIdCliente(), item01.getIdPedido());

        var item02 = gerarItemproducao();
        var testSetters = new ProducaoEntity();
        testSetters.setIdPedido(item02.getIdPedido());
        testSetters.setIdCliente(item02.getIdCliente());
        testSetters.setNumeroNaProducao(item02.getNumeroNaProducao());

        var item03 = gerarItemproducao();
        var testBuilder = ProducaoEntity.builder().idPedido(item03.getIdPedido()).idCliente(item03.getIdCliente()).numeroNaProducao(item03.getNumeroNaProducao()).build();

        var itens = List.of(testConstructor, testSetters, testBuilder, new ProducaoEntity(gerarItemproducao()), new ProducaoEntity(gerarItemproducao()));

        when(repository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<ProducaoEntity>(itens));

        var itensObtidos = producaoRepository.obterPedidos(0, 10);

        assertThat(itensObtidos).isNotNull();
        assertThat(itensObtidos).asList();
        assertThat(itensObtidos).hasSize(5);

        verify(repository, times(1)).findAll(any(PageRequest.class));
    }
}
