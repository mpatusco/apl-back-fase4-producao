package com.fiap.producao.bdd;

import com.fiap.producao.adapters.ProducaoDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinition {

    private Response response;
    private ProducaoDTO producaoResposta;
    private UUID idPedido;
    private UUID idCliente;
    private final String BASE_URI = "http://localhost:9092";
    private final String BASE_PATH = "/tech-challenge/producao";
    private final String PATH_JSON_SCHEMA = "schemas/producao.schema.json";
    private final String PATH_JSON_SCHEMA_PAGE = "schemas/producao-paginada.schema.json";

    @Quando("inserir pedido na producao")
    public ProducaoDTO inserir_pedido_na_producao() {
        idPedido = UUID.randomUUID();
        idCliente = UUID.randomUUID();
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/clientes/{idCliente}/pedidos/{idPedido}", idCliente, idPedido);

        return response.then().extract().as(ProducaoDTO.class);
    }


    @Entao("o pedido e inserido com sucesso")
    public void o_pedido_e_inserido_com_sucesso() {
        response.then().statusCode(HttpStatus.OK.value());
    }

    @E("deve ser apresentado")
    public void deve_ser_apresentado() {
        response.then().body(matchesJsonSchemaInClasspath(PATH_JSON_SCHEMA));
    }

    @Dado("que um pedido foi inserido na producao")
    public void que_um_pedido_foi_inserido_na_producao() {
        producaoResposta = inserir_pedido_na_producao();
    }

    @Quando("concluir um pedido")
    public void concluir_um_pedido() {
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .put("/{id}", idPedido);
    }

    @Entao("o pedido e concluido na producao")
    public void o_pedido_e_concluido_na_producao() {
        response.then().statusCode(HttpStatus.OK.value());
    }

    @Quando("remover um pedido")
    public void remover_um_pedido() {
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .delete("/{id}", idPedido);
    }

    @Entao("o pedido deve ser removido da producao")
    public void o_pedido_deve_ser_removido_da_producao() {
        response.then().statusCode(HttpStatus.OK.value());
    }

    @Quando("obter um pedido na producao")
    public void obter_um_pedido_na_producao() {
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .get("/{id}", idPedido);
    }

    @Entao("o pedido deve ser obtido")
    public void o_pedido_deve_ser_obtido() {
        response.then().statusCode(HttpStatus.OK.value());
    }

    @Quando("buscar pedidos na producao")
    public void buscar_pedidos_na_producao() {
        response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .get("/pedidos");
    }

    @Entao("deve buscar os pedidos")
    public void deve_buscar_os_pedidos() {
        response.then().statusCode(HttpStatus.OK.value());
    }

    @E("os pedidos devem ser apresentados")
    public void os_pedidos_devem_ser_apresentados() {
        response.then().body(matchesJsonSchemaInClasspath(PATH_JSON_SCHEMA_PAGE));
    }
}
