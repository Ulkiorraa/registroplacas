package br.com.ulkiorra.registroplacas.DAO.implDAO;

import br.com.ulkiorra.registroplacas.DAO.IPlacasDAO;
import br.com.ulkiorra.registroplacas.config.ConnectionFactory;
import br.com.ulkiorra.registroplacas.config.DbException;
import br.com.ulkiorra.registroplacas.model.Placas;

import java.sql.*;
import java.util.List;

public class PlacasDAO implements IPlacasDAO {
    private final Connection conn;
    public PlacasDAO(Connection conn){
        this.conn = conn;
    }

    @Override
    public Placas create(Placas placas) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String query = "INSERT INTO placas(placa, status, observation, dataestampagem, client_name, client_fone)VALUES (?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, placas.getPlaca());
            st.setString(2, placas.getStatus().toString());
            st.setString(3, placas.getObservation());
            st.setDate(4, Date.valueOf(placas.getDateEstampagem()));
            st.setString(5, placas.getNome());
            st.setString(6, placas.getTelefone());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            rs.next();
            Long idGerado = rs.getLong(1);
            placas.setId(idGerado);
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeResultSet(rs);
        }
        return placas;
    }

    @Override
    public Placas update(Placas placas) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Placas> FindAll() {
        return null;
    }

    @Override
    public List<Placas> FindByPlaca(String placa) {
        return null;
    }

    @Override
    public List<Placas> FindByName(String name) {
        return null;
    }
}
