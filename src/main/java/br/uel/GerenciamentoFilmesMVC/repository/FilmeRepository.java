package br.uel.GerenciamentoFilmesMVC.repository;

import br.uel.GerenciamentoFilmesMVC.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByNomeContainingIgnoreCase(String nome);
}

