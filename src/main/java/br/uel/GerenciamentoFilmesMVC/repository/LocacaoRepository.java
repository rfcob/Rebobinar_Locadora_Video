package br.uel.GerenciamentoFilmesMVC.repository;

import br.uel.GerenciamentoFilmesMVC.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
    // Ela busca todas as locações e já "engata" os dados do cliente e do filme
    @Query("SELECT l FROM Locacao l JOIN FETCH l.cliente JOIN FETCH l.filme ORDER BY l.dataLocacao DESC")
    List<Locacao> findAllWithClienteAndFilme();

    // Metodo, locações pendentes
    @Query("SELECT l FROM Locacao l JOIN FETCH l.cliente c JOIN FETCH l.filme f " +
            "WHERE l.dataDevolucao IS NULL " +
            "AND (LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(f.nome) LIKE LOWER(CONCAT('%', :termo, '%')))")
    List<Locacao> findLocacoesPendentesByTermo(@Param("termo") String termo);
}