package dataFactory;

import Pojo.ComponentePojo;

public class ComponenteDataFactory {
    public static ComponentePojo criarComponenteGenerico(String componenteNome){
        ComponentePojo componente = new ComponentePojo();
        componente.setComponenteNome(componenteNome);
        componente.setComponenteQuantidade(1);

        return componente;
    }
}
