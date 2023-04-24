package br.com.ulkiorra.registroplacas.DAO;

import br.com.ulkiorra.registroplacas.model.Client;

import java.util.List;

public interface IClientDAO {
    Client create(Client client);
    Client update(Client client);
    void delete(Long id);
    List<Client> FindAll();
    Client FindByName(String name);
    List<Client> FindByFone(String fone);
}
