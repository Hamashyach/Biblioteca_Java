package com.example.livraria.controller;

import com.example.livraria.model.Emprestimo;
import com.example.livraria.model.Livro;
import com.example.livraria.service.LivrariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * CRUD = CREATE READ UPDATE DELETE
 */

@RestController
@RequestMapping("/livraria")
public class LivrariaController {
	
	@Autowired
    private LivrariaService livrariaService;
    
	@PostMapping("/criar-tabelas")
    public String criarTabelas() {
        try {
            livrariaService.criarTabelaLivro();
            livrariaService.criarTabelaEmprestimo();
            return "Tabelas criadas com sucesso!";
        } catch (RuntimeException e) {
            return "Erro ao criar tabelas: " + e.getMessage();
        }
    }
	
	@PostMapping("/apagar-tabelas")
    public String apagarTabelas() {
        try {
            livrariaService.apagarTabelaEmprestimo();
            livrariaService.apagarTabelaLivro();
            return "Tabelas apagadas com sucesso!";
        } catch (RuntimeException e) {
            return "Erro ao apagar tabelas: " + e.getMessage();
        }
    }	
	
    // ==========================
    // Endpoints para Livros
    // ==========================
    
    //CREATE
    @PostMapping("/livros")
    public String criarLivro(@RequestBody Livro livro) {
        try {
            livrariaService.inserirLivro(livro);
            return "Livro inserido com sucesso!";
        } catch (RuntimeException e) {
            return "Erro ao inserir livro: " + e.getMessage();
        }
    }
    
    //READ
    @GetMapping("/livros/{id}")
    public Livro obterLivroPorId(@PathVariable Long id) {
        try {
            return livrariaService.obterLivroPorId(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao obter livro: " + e.getMessage());
        }
    }
    
    
    //READ ALL
    @GetMapping("/livros")
    public List<Livro> listarLivros() {
        try {
            return livrariaService.listarTodosLivros();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar livros: " + e.getMessage());
        }
    }
    
    
    //UPDATE
    @PutMapping("/livros/{id}")
    public String atualizarLivro(@PathVariable Long id, @RequestBody Livro livro) {
        try {
            livro.setId(id);
            livrariaService.atualizarLivro(livro);
            return "Livro atualizado com sucesso!";
        } catch (RuntimeException e) {
            return "Erro ao atualizar livro: " + e.getMessage();
        }
    }

    
    //DELETE
    @DeleteMapping("/livros/{id}")
    public String deletarLivro(@PathVariable Long id) {
        try {
            livrariaService.deletarLivro(id);
            return "Livro deletado com sucesso!";
        } catch (RuntimeException e) {
            return "Erro ao deletar livro: " + e.getMessage();
        }
    }

    // =============================
    // Endpoints para Empréstimos
    // =============================

    //CREATE
    @PostMapping("/emprestimos")
    public String criarEmprestimo(@RequestBody Emprestimo emprestimo) {
        try {
            livrariaService.inserirEmprestimo(emprestimo);
            return "Empréstimo criado com sucesso!";
        } catch (RuntimeException e) {
            return "Erro ao criar empréstimo: " + e.getMessage();
        }
    }
    
    //READ
    @GetMapping("/emprestimos/{id}")
    public Emprestimo obterEmprestimoPorId(@PathVariable Long id) {
        try {
            return livrariaService.obterEmprestimoPorId(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao obter empréstimo: " + e.getMessage());
        }
    }

    //READ ALL
    @GetMapping("/emprestimos")
    public List<Emprestimo> listarEmprestimos() {
        try {
            return livrariaService.listarTodosEmprestimos();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar empréstimos: " + e.getMessage());
        }
    }
    
    //UPDATE
    @PutMapping("/emprestimos/{id}")
    public String atualizarEmprestimo(@PathVariable Long id, @RequestBody Emprestimo emprestimo) {
        try {
            emprestimo.setId(id);
            livrariaService.atualizarEmprestimo(emprestimo);
            return "Empréstimo atualizado com sucesso!";
        } catch (RuntimeException e) {
            return "Erro ao atualizar empréstimo: " + e.getMessage();
        }
    }
    
    //DEVOLUCAO
    @PostMapping("/emprestimos/{id}/devolver")
    public String devolverEmprestimo(@PathVariable Long id) {
        try {
            livrariaService.devolverEmprestimo(id);
            return "Empréstimo devolvido com sucesso!";
        } catch (RuntimeException e) {
            return "Erro ao devolver empréstimo: " + e.getMessage();
        }
    }
    
    //DELETE
    @DeleteMapping("/emprestimos/{id}")
    public String deletarEmprestimo(@PathVariable Long id) {
        try {
            livrariaService.deletarEmprestimo(id);
            return "Empréstimo deletado com sucesso!";
        } catch (RuntimeException e) {
            return "Erro ao deletar empréstimo: " + e.getMessage();
        }
    }
}
