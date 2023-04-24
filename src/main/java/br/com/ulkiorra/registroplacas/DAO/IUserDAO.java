package br.com.ulkiorra.registroplacas.DAO;

import br.com.ulkiorra.registroplacas.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    User create(User user);
    User update(User user);
    void delete(Long id);
    List<User>findAll();
    Optional<User>findByUser(String user);
}
