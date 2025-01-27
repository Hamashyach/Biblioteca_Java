package com.example.livraria.service;

import com.example.livraria.dao.EmprestimoDAO;
import com.example.livraria.dao.LivroDAO;
import com.example.livraria.model.Emprestimo;
import com.example.livraria.model.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
//import java.util.Date;
import java.util.List;
//import java.util.Map;

@Service
public class LivrariaService {

	@Autowired
    private LivroDAO livroDAO;

    @Autowired
    private EmprestimoDAO emprestimoDAO;
    
    public void criarTabelaLivro() {
        try {
            livroDAO.criarTabela();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabelas: " + e.getMessage(), e);
        }
    }

    public void apagarTabelaLivro() {
        try {
            livroDAO.apagarTabela();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir livro: " + e.getMessage(), e);
        }
    }
    
    public void criarTabelaEmprestimo() {
        try {
            emprestimoDAO.criarTabela();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabelas: " + e.getMessage(), e);
        }
    }
    
    public void apagarTabelaEmprestimo() {
        try {
            emprestimoDAO.apagarTabela();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir livro: " + e.getMessage(), e);
        }
    }
    
    
    
    public void inserirLivro(Livro livro) {
        try {
            livroDAO.inserir(livro);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir livro: " + e.getMessage(), e);
        }
    }

    public Livro obterLivroPorId(Long id) {
        try {
            return livroDAO.obterPorId(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter livro por ID: " + e.getMessage(), e);
        }
    }

    public List<Livro> listarTodosLivros() {
        try {
            return livroDAO.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar livros: " + e.getMessage(), e);
        }
    }

    public void atualizarLivro(Livro livro) {
        try {
            livroDAO.atualizar(livro);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar livro: " + e.getMessage(), e);
        }
    }

    public void deletarLivro(Long id) {
        try {
            livroDAO.deletar(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar livro: " + e.getMessage(), e);
        }
    }

    public void inserirEmprestimo(Emprestimo emprestimo) {
        try {
            // Obter o livro associado ao empréstimo
            Livro livro = livroDAO.obterPorId(emprestimo.getLivroId());
            if (livro == null) {
                throw new RuntimeException("Livro com ID " + emprestimo.getLivroId() + " não encontrado.");
            }

            // Verificar se há estoque suficiente
            if (livro.getQuantidade() < emprestimo.getQuantidade()) {
                throw new RuntimeException("Quantidade insuficiente em estoque para o livro: " + livro.getTitulo());
            }

            // Atualizar a quantidade em estoque
            livro.setQuantidade(livro.getQuantidade() - emprestimo.getQuantidade());
            livroDAO.atualizar(livro);

            // Inserir o empréstimo no banco de dados
            emprestimoDAO.inserir(emprestimo);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir empréstimo: " + e.getMessage(), e);
        }
    }


    public Emprestimo obterEmprestimoPorId(Long id) {
        try {
            return emprestimoDAO.obterPorId(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter empréstimo por ID: " + e.getMessage(), e);
        }
    }

    public List<Emprestimo> listarTodosEmprestimos() {
        try {
            return emprestimoDAO.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar empréstimos: " + e.getMessage(), e);
        }
    }

    public void atualizarEmprestimo(Emprestimo emprestimo) {
        try {
            // Verifica se o empréstimo existe
            Emprestimo existente = emprestimoDAO.obterPorId(emprestimo.getId());
            if (existente == null) {
                throw new RuntimeException("Empréstimo com ID " + emprestimo.getId() + " não encontrado.");
            }

            // Atualiza o empréstimo no banco de dados
            emprestimoDAO.atualizar(emprestimo);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar empréstimo: " + e.getMessage(), e);
        }
    }
    
    public void devolverEmprestimo(Long emprestimoId) {
        try {
            // Obter o empréstimo pelo ID
            Emprestimo emprestimo = emprestimoDAO.obterPorId(emprestimoId);
            if (emprestimo == null) {
                throw new RuntimeException("Empréstimo com ID " + emprestimoId + " não encontrado.");
            }

            // Obter o livro associado ao empréstimo
            Livro livro = livroDAO.obterPorId(emprestimo.getLivroId());
            if (livro == null) {
                throw new RuntimeException("Livro com ID " + emprestimo.getLivroId() + " não encontrado.");
            }

            // Atualizar a quantidade em estoque do livro
            livro.setQuantidade(livro.getQuantidade() + emprestimo.getQuantidade());
            livroDAO.atualizar(livro);

            // Remover o empréstimo do banco de dados
            emprestimoDAO.deletar(emprestimoId);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao processar devolução: " + e.getMessage(), e);
        }
    }

    public void deletarEmprestimo(Long id) {
        try {
            emprestimoDAO.deletar(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar empréstimo: " + e.getMessage(), e);
        }
    }
}

