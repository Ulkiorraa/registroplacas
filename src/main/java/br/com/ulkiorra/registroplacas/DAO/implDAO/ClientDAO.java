package br.com.ulkiorra.registroplacas.DAO.implDAO;

import br.com.ulkiorra.registroplacas.DAO.IClientDAO;
import br.com.ulkiorra.registroplacas.config.ConnectionFactory;
import br.com.ulkiorra.registroplacas.config.DbException;
import br.com.ulkiorra.registroplacas.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements IClientDAO {
    private final Connection conn;

    public ClientDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Client create(Client client) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO clientes(nome, telefone, email, datacadastro, cpf, cnpj, preco_padrao_par, preco_padrao_und)VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, client.getNome());
            st.setString(2, client.getTelefone());
            st.setString(3, client.getEmail());
            st.setDate(4, Date.valueOf(client.getDateCad()));
            st.setString(5, client.getCpf());
            st.setString(6, client.getCnpj());
            st.setFloat(7, client.getPreco_par());
            st.setFloat(8, client.getPreco_und());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            rs.next();
            Long idGerado = rs.getLong(1);
            client.setId(idGerado);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeResultSet(rs);
        }
        return client;
    }

    @Override
    public Client update(Client client) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Client> FindAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM clientes";
            List<Client> list = new ArrayList<>();
            st = conn.prepareStatement(query);
            st.executeQuery();
            rs = st.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setNome(rs.getString("nome"));
                client.setTelefone(rs.getString("telefone"));
                client.setEmail(rs.getString("email"));
                client.setDateCad(rs.getDate("datacadastro").toLocalDate());
                client.setCpf(rs.getString("cpf"));
                client.setCnpj(rs.getString("cnpj"));
                client.setPreco_par(rs.getFloat("preco_padrao_par"));
                client.setPreco_par(rs.getFloat("preco_padrao_und"));
                list.add(client);
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
    public Client FindByName(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Client client = null;
        try {
            String query = "SELECT * FROM clientes WHERE nome = ?";
            st = conn.prepareStatement(query);
            st.setString(1, name);
            st.executeQuery();
            rs = st.executeQuery();
            while (rs.next()) {
                client = new Client(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getFloat("preco_padrao_par"),
                        rs.getFloat("preco_padrao_und"),
                        rs.getString("cpf"),
                        rs.getString("cnpj"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getDate("datacadastro").toLocalDate()
                );
            }
            return client;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeResultSet(rs);
        }
    }

    @Override
    public List<Client> FindByFone(String fone) {
        return null;
    }
}
