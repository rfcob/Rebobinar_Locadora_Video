package br.uel.GerenciamentoFilmesMVC.repository;

import br.uel.GerenciamentoFilmesMVC.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

    /**
     * Busca todas as entidades Locacao, carregando antecipadamente (eager loading via JOIN FETCH)
     * Os resultados são ordenados pela data de locação em ordem decrescente (mais recentes primeiro).
     * Utiliza JPQL.
     */
    @Query("SELECT l FROM Locacao l JOIN FETCH l.cliente JOIN FETCH l.filme ORDER BY l.dataLocacao DESC")
    List<Locacao> findAllWithClienteAndFilme();

    @Query("SELECT l FROM Locacao l JOIN FETCH l.cliente c JOIN FETCH l.filme f " +
            "WHERE l.dataDevolucao IS NULL " +
            "AND (LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(f.nome) LIKE LOWER(CONCAT('%', :termo, '%')))")
    List<Locacao> findLocacoesPendentesByTermo(@Param("termo") String termo);

    // Busca locações ATIVAS (sem data de devolução) para um cliente específico,
    List<Locacao> findByClienteIdAndDataDevolucaoIsNullOrderByDataLocacaoDesc(Long clienteId);

    // Busca locações JÁ DEVOLVIDAS para um cliente específico,
    List<Locacao> findByClienteIdAndDataDevolucaoIsNotNullOrderByDataDevolucaoDesc(Long clienteId);
}