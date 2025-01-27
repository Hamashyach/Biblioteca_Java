package com.example.livraria.dao;

import com.example.livraria.dbconn.JdbcConnection;
import com.example.livraria.interfaces.InterfaceDAO;
import com.example.livraria.model.Livro;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LivroDAO implements InterfaceDAO<Livro> {

    @Override
    public void criarTabela() throws SQLException {
        try {
            String createTableSql = "CREATE TABLE IF NOT EXISTS livro (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "titulo VARCHAR(255) NOT NULL, " +
                    "autor VARCHAR(255) NOT NULL, " +
                    "preco DOUBLE NOT NULL, " +
                    "quantidade INT NOT NULL)";

            try (Connection connection = JdbcConnection.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(createTableSql);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao criar tabela de livros: " + e.getMessage(), e);
        }
    }

    @Override
    public void apagarTabela() throws SQLException {
        try {
            String dropTableSql = "DROP TABLE IF EXISTS livro";
            try (Connection connection = JdbcConnection.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(dropTableSql);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao apagar tabela de livros: " + e.getMessage(), e);
        }
    }

    @Override
    public void inserir(Livro livro) throws SQLException {
        try {
            String insertSql = "INSERT INTO livro (titulo, autor, preco, quantidade) VALUES (?, ?, ?, ?)";
            try (Connection connection = JdbcConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(insertSql)) {
                stmt.setString(1, livro.getTitulo());
                stmt.setString(2, livro.getAutor());
                stmt.setDouble(3, livro.getPreco());
                stmt.setDouble(4, livro.getQuantidade());
                stmt.executeUpdate();
            }
              
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir livro: " + e.getMessage(), e);
        }
    }

    @Override
    public Livro obterPorId(Long id) throws SQLException {
        try {
            String querySql = "SELECT * FROM livro WHERE id = ?";
            try (Connection connection = JdbcConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(querySql)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Livro livro = new Livro();
                    livro.setId(rs.getLong("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setPreco(rs.getDouble("preco"));
                    livro.setQuantidade(rs.getInt("quantidade"));
                    return livro;
                } else {
                    throw new SQLException("Livro com ID " + id + " não encontrado.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao obter livro por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Livro> listarTodos() throws SQLException {
        try {
            List<Livro> livros = new ArrayList<>();
            String querySql = "SELECT * FROM livro";

            try (Connection connection = JdbcConnection.getConnection();
                 Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(querySql)) {
                while (rs.next()) {
                    Livro livro = new Livro();
                    livro.setId(rs.getLong("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setPreco(rs.getDouble("preco"));
                    livro.setQuantidade(rs.getInt("quantidade"));
                    livros.add(livro);
                }
            }
            return livros;
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar todos os livros: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Livro livro) throws SQLException {
        try {
            String updateSql = "UPDATE livro SET titulo = ?, autor = ?, preco = ?, quantidade = ? WHERE id = ?";
            try (Connection connection = JdbcConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(updateSql)) {
                stmt.setString(1, livro.getTitulo());
                stmt.setString(2, livro.getAutor());
                stmt.setDouble(3, livro.getPreco());
                stmt.setDouble(4, livro.getQuantidade());
                stmt.setLong(5, livro.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar livro: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(Long id) throws SQLException {
        try {
            String deleteSql = "DELETE FROM livro WHERE id = ?";
            try (Connection connection = JdbcConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(deleteSql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao deletar livro: " + e.getMessage(), e);
        }
    }
}

