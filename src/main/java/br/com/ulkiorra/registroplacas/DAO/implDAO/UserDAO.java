package br.com.ulkiorra.registroplacas.DAO.implDAO;

import br.com.ulkiorra.registroplacas.DAO.IUserDAO;
import br.com.ulkiorra.registroplacas.config.ConnectionFactory;
import br.com.ulkiorra.registroplacas.config.DbException;
import br.com.ulkiorra.registroplacas.model.User;
import br.com.ulkiorra.registroplacas.util.Encript;

import java.sql.*;
import java.util.ArrayList;
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
            st.setString(2, Encript.encryptSHA1(user.getPassword()));
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
        PreparedStatement st = null;
        try{
            String query = "UPDATE user SET " + "user = ?, senha = ?" + "WHERE id = ?";
            st = conn.prepareStatement(query);
            st.setString(1, user.getUser());
            st.setString(2, Encript.encryptSHA1(user.getPassword()));
            st.setLong(3, user.getId());
            st.executeUpdate();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            ConnectionFactory.closeStatement(st);
        }
        return user;
    }

    @Override
    public void delete(User id) {
        PreparedStatement st = null;
        try{
            String query = "DELETE FROM user WHERE id = ?";
            st = conn.prepareStatement(query);
            st.setLong(1, id.getId());
            st.executeUpdate();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            ConnectionFactory.closeStatement(st);
        }
    }

    @Override
    public List<User> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM user";
            List<User> list = new ArrayList<>();
            st = conn.prepareStatement(query);
            st.executeQuery();
            rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUser(rs.getString("user"));
                user.setPassword(Encript.encryptSHA1(rs.getString("senha")));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeResultSet(rs);
        }
    }

    @Override
    public Optional<User> findByUser(String user) {
        return Optional.empty();
    }
}
