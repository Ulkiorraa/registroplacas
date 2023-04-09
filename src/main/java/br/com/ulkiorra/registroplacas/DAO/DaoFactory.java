package br.com.ulkiorra.registroplacas.DAO;

import br.com.ulkiorra.registroplacas.DAO.implDAO.UserDAO;
import br.com.ulkiorra.registroplacas.config.ConnectionFactory;

public class DaoFactory {
    public static IUserDAO createUserDAO(){
        return new UserDAO(ConnectionFactory.getConnection());
    }
}
