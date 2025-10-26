package br.uel.GerenciamentoFilmesMVC.repository;

import br.uel.GerenciamentoFilmesMVC.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    Optional<Cliente> findByEmail(String email);

    // para o DataSeeder
    Optional<Cliente> findByCpf(String cpf);
}