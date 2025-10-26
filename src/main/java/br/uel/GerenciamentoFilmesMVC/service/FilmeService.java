package br.uel.GerenciamentoFilmesMVC.service;

import br.uel.GerenciamentoFilmesMVC.model.Filme;
import br.uel.GerenciamentoFilmesMVC.repository.FilmeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {
    private final FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    // Listar todos os filmes ordenados por nome
    public List<Filme> listarFilmes() {
        return filmeRepository.findAll(Sort.by("nome").ascending());
    }

    // Buscar filme por ID
    public Filme buscarFilme(Long id) {
        return filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com id: " + id));
    }

    // Cadastrar novo filme
    public void cadastrarFilme(Filme f) {
        filmeRepository.save(f);
    }

    // Atualizar filme existente
    public void atualizarFilme(Long id, Filme filmeAtualizado) {
        Filme filme = filmeRepository.findById(id).orElse(null);

        if (filme != null) {
            filme.setNome(filmeAtualizado.getNome());
            filme.setAnoLancamento(filmeAtualizado.getAnoLancamento());
            filme.setCategoria(filmeAtualizado.getCategoria());
            filme.setDuracao(filmeAtualizado.getDuracao());
            filme.setVencedorOscar(filmeAtualizado.getVencedorOscar());
            filme.setDirecao(filmeAtualizado.getDirecao());
            filme.setElencoPrincipal(filmeAtualizado.getElencoPrincipal());
            filme.setCopiasDisponiveis(filmeAtualizado.getCopiasDisponiveis());
            filmeRepository.save(filme);
        } else {
            throw new RuntimeException("Filme não encontrado com id: " + id);
        }
    }

    // Excluir filme
    public void excluirFilme(Long id) {
        if (!filmeRepository.existsById(id)) {
            throw new RuntimeException("Filme não encontrado com id: " + id);
        }
        filmeRepository.deleteById(id);
    }

    public List<Filme> pesquisarFilmesPorNome(String termo) {
        return filmeRepository.findByNomeContainingIgnoreCase(termo);
    }
}
