package br.com.ulkiorra.registroplacas.DAO.implDAO;

import br.com.ulkiorra.registroplacas.DAO.IUserDAO;
import br.com.ulkiorra.registroplacas.config.ConnectionFactory;
import br.com.ulkiorra.registroplacas.config.DbException;
import br.com.ulkiorra.registroplacas.model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDAO implements IUserDAO {
    private final Connection conn;

    public UserDAO(Connection conn){
        this.conn = conn;
    }

    @Override
    public User create(User user) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String query = "INSERT INTO user(user, senha)VALUES (?, ?)";
            st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, user.getUser());
            st.setString(2, user.getPassword());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            rs.next();
            Long idGerado = rs.getLong(1);
            user.setId(idGerado);
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeResultSet(rs);
        }
        return user;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findByUser(String user) {
        return Optional.empty();
    }
}
