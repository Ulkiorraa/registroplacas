package br.com.ulkiorra.registroplacas.DAO;

import br.com.ulkiorra.registroplacas.DAO.implDAO.ClientDAO;
import br.com.ulkiorra.registroplacas.DAO.implDAO.PlacasDAO;
import br.com.ulkiorra.registroplacas.DAO.implDAO.UserDAO;
import br.com.ulkiorra.registroplacas.config.ConnectionFactory;

public class DaoFactory {
    public static IUserDAO createUserDAO(){
        return new UserDAO(ConnectionFactory.getConnection());
    }
    public static IPlacasDAO createPlacasDAO(){
        return new PlacasDAO(ConnectionFactory.getConnection());
    }
    public static IClientDAO createClientDAO(){
        return new ClientDAO(ConnectionFactory.getConnection());
    }
}