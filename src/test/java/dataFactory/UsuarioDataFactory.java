package dataFactory;

import Pojo.UsuarioPojo;

public class UsuarioDataFactory {

    public static UsuarioPojo criarUsuarioAdministrador(){
        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioLogin("fernandomc");
        usuario.setUsuarioSenha("123456");

        return usuario;
    }
}
