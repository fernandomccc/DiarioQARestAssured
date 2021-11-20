package dataFactory;

import Pojo.ComponentePojo;
import Pojo.ProdutoPojo;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDataFactory {
    public static ProdutoPojo criarProdutoGenericoComValorDe(double valor){
        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoNome("Dell Gamer G#");
        produto.setProdutoValor(valor);
        List<String> cores = new ArrayList<>();
        cores.add("Preto");
        cores.add("Azul");
        produto.setProdutoCores(cores);
        produto.setProdutoUrlMock("");

        List<ComponentePojo> componentes = new ArrayList<>();
        ComponentePojo componente = new ComponentePojo();
        componente.setComponenteNome("Carregador");
        componente.setComponenteQuantidade(1);

        componentes.add(componente);
        produto.setComponentes(componentes);

        return produto;
    }
}
