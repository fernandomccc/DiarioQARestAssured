package modulos.produto;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import Pojo.ComponentePojo;
import Pojo.ProdutoPojo;
import Pojo.UsuarioPojo;
import dataFactory.ComponenteDataFactory;
import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioDataFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Testes de API Rest do modulo de produto.")

public class ProdutoTest {
    private String token;

    @BeforeEach
    public void beforeEach() {
        baseURI = "http://165.227.93.41";
        basePath = "/lojinha";
        //Obter Token do usuario admin

        this.token = given()
                .contentType(ContentType.JSON)
                    .body(UsuarioDataFactory.criarUsuarioAdministrador())
                .when()
                    .post("/v2/login")
                .then()
                    .extract()
                        .path("data.token");
        }

    @Test
    @DisplayName("Validar cadastro de produto com valor abaixo do minimo permitido.")
    public void testValidarCadastroProdutoValorAbaixoDoPermitido(){

        given()
            .contentType(ContentType.JSON)
            .header("token",this.token)
            .body(ProdutoDataFactory.criarProdutoGenericoComValorDe(0.00))
        .when()
                .post("/v2/produtos")

        .then()
            .assertThat()
                .body("error",equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }

    @Test
    @DisplayName("Validar cadastro de produto com valor acima do minimo permitido.")
    public void testValidarCadastroProdutoValorAcimaDoPermitido(){

        given()
                .contentType(ContentType.JSON)
                .header("token",this.token)
                .body(ProdutoDataFactory.criarProdutoGenericoComValorDe(7000.01))
        .when()
            .post("/v2/produtos")

        .then()
            .assertThat()
                .body("error",equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);

    }

    @Test
    @DisplayName("Validar cadastro de produto com valor dentro do intervalo permitido..")
    public void testValidarCadastroProdutoValorRegular(){

        given()
                .contentType(ContentType.JSON)
                .header("token",this.token)
                .body(ProdutoDataFactory.criarProdutoGenericoComValorDe(5000.00))
        .when()
            .post("/v2/produtos")

        .then()
            .assertThat()
                .body("message",equalTo("Produto adicionado com sucesso"))
                .statusCode(201);

    }


    @Test
    @DisplayName("Validar busca de todos os produtos do usuario")
    public void testValidarBuscaTodosOsProdutosDoUsuario() {

        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
        .when()
            .get("/v2/produtos")

        .then()
                .log().all()
            .assertThat()
                .body("message", equalTo("Listagem de produtos realizada com sucesso"))
                .statusCode(200);
    }

    @Test
    @DisplayName("Validar busca de produtos do usuario pelo ID")
    public void testValidarBuscaProdutoByID() {

        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
        .when()
            .get("/v2/produtos/39667")

        .then()
               .log().all()
            .assertThat()
                .body("message", equalTo("Detalhando dados do produto"))
                .statusCode(200);
    }

    @Test
    @DisplayName("Validar alteração de dados do produto do usuario pelo ID")
    public void testAlterarProdutoById() {

        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
            .body(ProdutoDataFactory.criarProdutoGenericoComValorDe(5500))
        .when()
            .put("/v2/produtos/38808")

        .then()
            .assertThat()
                .body("message", equalTo("Produto alterado com sucesso"))
                .statusCode(200);
    }

    /*@Test
    @DisplayName("Validar exclusão de produtos do usuario pelo ID")
    public void testValidarExcluirProdutoByID() {

        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
        .when()
            .delete("/v2/produtos/39577")

        .then()
            .assertThat()
                .statusCode(204);
    }*/

    @Test
    @DisplayName("Validar cadastro de novo componente no produto pelo ID.")
    public void testValidarAdicaoDeNovoComponenteAoProdutoById(){

        given()
            .contentType(ContentType.JSON)
            .header("token",this.token)
            .body(ComponenteDataFactory.criarComponenteGenerico("Cabo USB 11.0"))
        .when()
            .post("/v2/produtos/39667/componentes")

        .then()
                .log().all()
            .assertThat()
                .body("message",equalTo("Componente de produto adicionado com sucesso"))
                .statusCode(201);

    }

    @Test
    @DisplayName("Validar Busca de componente do produto pelo ID.")
    public void testValidarBuscaDeComponenteDeProdutoById(){

        given()
            .contentType(ContentType.JSON)
            .header("token",this.token)
        .when()
            .get("/v2/produtos/39667/componentes")

        .then()
                .log().all()
            .assertThat()
                .body("message",equalTo("Listagem de componentes de produto realizada com sucesso"))
                .statusCode(200);

    }

    @Test
    @DisplayName("Validar alteracao de componente do produto pelo ID do componente.")
    public void testValidarBuscaDeComponenteDeProdutoByIdComponente(){

        given()
            .contentType(ContentType.JSON)
            .header("token",this.token)
            .body(ComponenteDataFactory.criarComponenteGenerico("Cabo USB5.0"))
        .when()
            .put("/v2/produtos/39667/componentes/44895")

        .then()
                .log().all()
            .assertThat()
                .body("message",equalTo("Componente de produto alterado com sucesso"))
                .statusCode(200);

    }

    @Test
    @DisplayName("Validar exclusao de componente do produto pelo ID do componente.")
    public void testValidarExclusaoDeComponenteDeProdutoByIdComponente(){

        given()
            .contentType(ContentType.JSON)
            .header("token",this.token)

        .when()
            .delete("/v2/produtos/39667/componentes/44895")

        .then()
            .log().all()
            .assertThat()
                .statusCode(204);

    }


}
