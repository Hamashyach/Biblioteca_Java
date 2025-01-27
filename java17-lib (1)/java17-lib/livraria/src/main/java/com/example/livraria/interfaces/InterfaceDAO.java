package com.example.livraria.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceDAO<T> {
    void criarTabela() throws SQLException;
    void apagarTabela() throws SQLException;
    void inserir(T entity) throws SQLException;
    T obterPorId(Long id) throws SQLException;
    List<T> listarTodos() throws SQLException;
    void atualizar(T entity) throws SQLException;
    void deletar(Long id) throws SQLException;
}
