package com.example.livraria.dao;

import com.example.livraria.dbconn.JdbcConnection;
import com.example.livraria.interfaces.InterfaceDAO;
import com.example.livraria.model.Emprestimo;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmprestimoDAO implements InterfaceDAO<Emprestimo> {

    @Override
    public void criarTabela() throws SQLException {
        String createTableSql = "CREATE TABLE IF NOT EXISTS emprestimo (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "livro_id BIGINT NOT NULL, " +
                "quantidade INT NOT NULL, " +
                "data_emprestimo DATE NOT NULL, " +
                "data_devolucao DATE NOT NULL, " +
                "FOREIGN KEY (livro_id) REFERENCES livro(id))";

        try (Connection connection = JdbcConnection.getConnection();
            Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSql);
        }
    }

    @Override
    public void apagarTabela() throws SQLException {
        String dropTableSql = "DROP TABLE IF EXISTS emprestimo";

        try (Connection connection = JdbcConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(dropTableSql);
        }
    }

    @Override
    public void inserir(Emprestimo emprestimo) throws SQLException {
        String insertSql = "INSERT INTO emprestimo (livro_id, quantidade, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";
        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, emprestimo.getLivroId());
            stmt.setDouble(2, emprestimo.getQuantidade());
            stmt.setString(3, emprestimo.getDataEmprestimo());
            stmt.setString(4, emprestimo.getDataDevolucao());

            stmt.executeUpdate();
        }
    }

    @Override
    public Emprestimo obterPorId(Long id) throws SQLException {
        String querySql = "SELECT * FROM emprestimo WHERE id = ?";
        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(querySql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getLong("id"));
                emprestimo.setLivroId(rs.getLong("livro_id"));
                emprestimo.setQuantidade(rs.getDouble("quantidade"));
                emprestimo.setDataEmprestimo(rs.getString("data_emprestimo"));
                emprestimo.setDataDevolucao(rs.getString("data_devolucao"));
                return emprestimo;
            }
        }
        return null;
    }

    @Override
    public List<Emprestimo> listarTodos() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String querySql = "SELECT * FROM emprestimo";

        try (Connection connection = JdbcConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(querySql)) {
            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getLong("id"));
                emprestimo.setLivroId(rs.getLong("livro_id"));
                emprestimo.setQuantidade(rs.getDouble("quantidade"));
                emprestimo.setDataEmprestimo(rs.getString("data_emprestimo"));
                emprestimo.setDataDevolucao(rs.getString("data_devolucao"));
                emprestimos.add(emprestimo);
            }
        }
        return emprestimos;
    }

    @Override
    public void atualizar(Emprestimo emprestimo) throws SQLException {
        String updateSql = "UPDATE emprestimo SET livro_id = ?, quantidade = ?, data_emprestimo = ?, data_devolucao = ? WHERE id = ?";
        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateSql)) {

            // Configura os parâmetros
            stmt.setLong(1, emprestimo.getLivroId());
            stmt.setDouble(2, emprestimo.getQuantidade());
            
            // Configura a data do empréstimo

            	stmt.setString(3, emprestimo.getDataEmprestimo());

            // Configura a data de devolução
            
            	stmt.setString(4, emprestimo.getDataDevolucao());
            
            // Define o ID
            stmt.setLong(5, emprestimo.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deletar(Long id) throws SQLException {
        String deleteSql = "DELETE FROM emprestimo WHERE id = ?";
        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(deleteSql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

}

