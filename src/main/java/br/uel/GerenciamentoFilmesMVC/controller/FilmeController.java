package br.uel.GerenciamentoFilmesMVC.controller;

import br.uel.GerenciamentoFilmesMVC.model.Filme;
import br.uel.GerenciamentoFilmesMVC.service.FilmeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeService filmeService;

    // Constante para a chave do filtro na sessão
    private static final String FILTRO_FILME_SESSAO = "filtroFilmeTermo";

    @Autowired
    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @GetMapping
    public String listar(@RequestParam(name = "termoPesquisa", required = false) String termoPesquisa,
                         HttpSession session,
                         Model model) {

        String termoAtivo;

        // Verifica se uma nova pesquisa foi submetida
        if (termoPesquisa != null) {
            if (termoPesquisa.trim().isEmpty()) {
                session.removeAttribute(FILTRO_FILME_SESSAO); // Limpa se termo vazio
                termoAtivo = null;
            } else {
                session.setAttribute(FILTRO_FILME_SESSAO, termoPesquisa); // Guarda na sessão
                termoAtivo = termoPesquisa;
            }
        } else {
            // Se não veio da requisição, busca na sessão
            termoAtivo = (String) session.getAttribute(FILTRO_FILME_SESSAO);
        }

        List<Filme> filmes;
        // Decide se busca todos ou filtra
        if (termoAtivo != null && !termoAtivo.isEmpty()) {
            filmes = filmeService.pesquisarFilmesPorNome(termoAtivo);
        } else {
            filmes = filmeService.listarFilmes();
        }

        // Adiciona ao modelo
        model.addAttribute("filmes", filmes);
        model.addAttribute("termoPesquisaAtivo", termoAtivo);
        return "filmes/lista_bootstrap";

    }

    @GetMapping("/limpar-filtro")
    public String limparFiltro(HttpSession session) {
        session.removeAttribute(FILTRO_FILME_SESSAO);
        return "redirect:/filmes";
    }

    // Exibe a página de opções relacionadas a filmes
    @GetMapping("/opcoes")
    public String opcoesFilmes() {
        return "filmes/opcoes";
    }

    // Endpoint para pesquisa de filmes via API
    @GetMapping("/api/pesquisa")
    @ResponseBody
    public List<Filme> pesquisarApi(@RequestParam(required = false, defaultValue = "") String termo) {
        return filmeService.pesquisarFilmesPorNome(termo);
    }

    // Abre o formulário para cadastrar um novo filme
    @GetMapping("/novo")
    public String abrirCadastro(Model model) {
        model.addAttribute("filme", new Filme());
        return "filmes/form_bootstrap";

    }

    // Processa o envio do formulário de cadastro de filme
    @PostMapping
    public String cadastrar(@Valid @ModelAttribute Filme filme,
                            BindingResult erros,
                            RedirectAttributes ra,
                            Model model) {
        if (erros.hasErrors()) {
            return "filmes/form_bootstrap";
        }
        try {
            filmeService.cadastrarFilme(filme);
            ra.addFlashAttribute("success", "Filme cadastrado!");
            return "redirect:/filmes";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("filme", filme);
            ra.addFlashAttribute("error", "Erro ao cadastrar filme: Verifique os dados."); // Mensagem mais genérica
            return "filmes/form_bootstrap";
        }
    }

    // Abre o formulário de edição com os dados do filme
    @GetMapping("/editar/{id}")
    public String abrirEdicao(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("filme", filmeService.buscarFilme(id));
            return "filmes/form_bootstrap";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/filmes";
        }
    }

    // Processa o envio do formulário de edição de filme
    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute Filme filme,
                            BindingResult erros,
                            RedirectAttributes ra,
                            Model model) {
        if (erros.hasErrors()) {
            filme.setId(id);
            return "filmes/form_bootstrap";
        }
        try {
            filmeService.atualizarFilme(id, filme);
            ra.addFlashAttribute("success", "Filme atualizado!");
            return "redirect:/filmes";
        } catch (DataIntegrityViolationException e) {
            filme.setId(id);
            model.addAttribute("filme", filme);
            ra.addFlashAttribute("error", "Erro ao atualizar filme: Verifique os dados.");
            return "filmes/form_bootstrap";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/filmes";
        }
    }

    // Exclui um filme pelo ID
    @DeleteMapping("/{id}")
    public String excluir(@PathVariable Long id,
                          RedirectAttributes ra) {
        try {
            filmeService.excluirFilme(id);
            ra.addFlashAttribute("success", "Filme excluído!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/filmes";
    }

}