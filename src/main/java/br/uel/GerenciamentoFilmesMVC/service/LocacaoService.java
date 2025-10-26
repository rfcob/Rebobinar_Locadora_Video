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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private FilmeRepository filmeRepository;


    //Supervisionar  o cadastrar multiplas locações
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cadastrarMultiplasLocacoes(LocacaoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
        List<Long> filmeIds = dto.getFilmeIds();
        List<Filme> filmes = filmeRepository.findAllById(filmeIds);
        if (filmes.size() != filmeIds.size()) {
            throw new RuntimeException("Um ou mais filmes não foram encontrados!");
        }
        for (Filme filme : filmes) {
            Locacao novaLocacao = new Locacao();
            novaLocacao.setCliente(cliente);
            novaLocacao.setFilme(filme);
            novaLocacao.setDataLocacao(dto.getDataLocacao());
            novaLocacao.setDataDevolucao(dto.getDataDevolucao());
            locacaoRepository.saveAndFlush(novaLocacao);
        }
    }

    @Transactional
    public void registrarDevolucao(Long id) {
        Locacao locacao = buscarLocacao(id);
        if (locacao.getDataDevolucao() != null) {
            throw new RuntimeException("Este filme já foi devolvido.");
        }
        locacao.setDataDevolucao(LocalDate.now());
        locacaoRepository.save(locacao);
    }

    public List<Locacao> listarLocacoes() {
        return locacaoRepository.findAllWithClienteAndFilme();
    }

    public void excluirLocacao(Long id) {
        if (!locacaoRepository.existsById(id)) {
            throw new RuntimeException("Locação não encontrada com id: " + id);
        }
        locacaoRepository.deleteById(id);
    }

    public Locacao buscarLocacao(Long id) {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locação não encontrada com id: " + id));
    }


    public List<Locacao> pesquisarLocacoesPendentes(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return List.of();
        }
        return locacaoRepository.findLocacoesPendentesByTermo(termo);
    }

    //DASHBOARD DO CLIENTE

    // Busca as locações ativas de um cliente específico.
    public List<Locacao> buscarLocacoesAtivasPorCliente(Long clienteId) {
        return locacaoRepository.findByClienteIdAndDataDevolucaoIsNullOrderByDataLocacaoDesc(clienteId);
    }

    //Busca o histórico de locações (já devolvidas) de um cliente específico.
    public List<Locacao> buscarHistoricoLocacoesPorCliente(Long clienteId) {
        return locacaoRepository.findByClienteIdAndDataDevolucaoIsNotNullOrderByDataDevolucaoDesc(clienteId);
    }
}