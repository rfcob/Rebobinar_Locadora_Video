package br.uel.GerenciamentoFilmesMVC.service;

import br.uel.GerenciamentoFilmesMVC.dto.LocacaoDTO;
import br.uel.GerenciamentoFilmesMVC.model.Cliente;
import br.uel.GerenciamentoFilmesMVC.model.Filme;
import br.uel.GerenciamentoFilmesMVC.model.Locacao;
import br.uel.GerenciamentoFilmesMVC.repository.ClienteRepository;
import br.uel.GerenciamentoFilmesMVC.repository.FilmeRepository;
import br.uel.GerenciamentoFilmesMVC.repository.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation; // IMPORTAR
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LocacaoService {

    // Injeta os repositórios
    @Autowired
    private LocacaoRepository locacaoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private FilmeRepository filmeRepository;

    /**
     * Cadastra múltiplas locações com base nos dados recebidos via DTO.
     * Cada filme selecionado gera uma locação separada para o mesmo cliente.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cadastrarMultiplasLocacoes(LocacaoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        // Busca os filmes selecionados
        List<Long> filmeIds = dto.getFilmeIds();
        List<Filme> filmes = filmeRepository.findAllById(filmeIds);

        // Verifica se todos os filmes foram encontrados
        if (filmes.size() != filmeIds.size()) {
            throw new RuntimeException("Um ou mais filmes não foram encontrados!");
        }

        // Para cada filme, cria uma nova locação
        for (Filme filme : filmes) {
            Locacao novaLocacao = new Locacao();
            novaLocacao.setCliente(cliente);
            novaLocacao.setFilme(filme);
            novaLocacao.setDataLocacao(dto.getDataLocacao());
            novaLocacao.setDataDevolucao(dto.getDataDevolucao());

            // Salva imediatamente no banco
            locacaoRepository.saveAndFlush(novaLocacao);
        }
    }

    /**
     * Registra a devolução de um filme, atualizando a data de devolução.
     * Se o filme já tiver sido devolvido, lança uma exceção.
     */
    @Transactional
    public void registrarDevolucao(Long id) {
        Locacao locacao = buscarLocacao(id);
        if (locacao.getDataDevolucao() != null) {
            throw new RuntimeException("Este filme já foi devolvido.");
        }
        locacao.setDataDevolucao(LocalDate.now());
        locacaoRepository.save(locacao);
    }

    /**
     * Lista todas as locações, incluindo os dados de cliente e filme.
     */
    public List<Locacao> listarLocacoes() {
        return locacaoRepository.findAllWithClienteAndFilme();
    }

    /**
     * Exclui uma locação pelo ID, se ela existir.
     */
    public void excluirLocacao(Long id) {
        if (!locacaoRepository.existsById(id)) {
            throw new RuntimeException("Locação não encontrada com id: " + id);
        }
        locacaoRepository.deleteById(id);
    }

    /**
     * Busca uma locação específica pelo ID.
     */
    public Locacao buscarLocacao(Long id) {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locação não encontrada com id: " + id));
    }

    /**
     * Pesquisa locações pendentes de devolução com base em um termo (nome do cliente ou filme).
        */
    public List<Locacao> pesquisarLocacoesPendentes(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return List.of(); // Retorna uma lista vazia se a busca for em branco
        }
        return locacaoRepository.findLocacoesPendentesByTermo(termo);
    }
}