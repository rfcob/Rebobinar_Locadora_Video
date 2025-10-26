package br.uel.GerenciamentoFilmesMVC.controller;

import br.uel.GerenciamentoFilmesMVC.model.Filme;
import br.uel.GerenciamentoFilmesMVC.service.FilmeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping; // Verifique o import
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

// Define esta classe como um controlador
@Controller
@RequestMapping("/filmes")
public class FilmeController {

    // Injeta o serviço responsável pela lógica de negócio dos filmes
    //private: somente filme controller deve acessar
    private final FilmeService filmeService;

    // Construtor com injeção de dependência do FilmeService. Instâncias automáticas
    @Autowired
    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    // Exibe a página de opções relacionadas a filmes
    @GetMapping("/opcoes")
    public String opcoesFilmes() {
        return "filmes/opcoes"; // Retorna a view "opcoes"
    }

    // Exibe a lista de todos os filmes cadastrados
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("filmes", filmeService.listarFilmes()); // Adiciona a lista ao modelo
        return "filmes/lista_bootstrap"; // Retorna a view da lista
    }

    // Endpoint para pesquisa de filmes via API
    @GetMapping("/api/pesquisa")
    @ResponseBody // Retorna os dados diretamente como JSON
    public List<Filme> pesquisarApi(@RequestParam(required = false, defaultValue = "") String termo) {
        return filmeService.pesquisarFilmesPorNome(termo);
    }

    // Abre o formulário para cadastrar um novo filme
    @GetMapping("/novo")
    public String abrirCadastro(Model model) {
        model.addAttribute("filme", new Filme()); // Cria um objeto vazio para preencher o formulário
        return "filmes/form_bootstrap"; // Retorna a view do formulário
    }

    // Processa o envio do formulário de cadastro de filme
    @PostMapping
    public String cadastrar(@Valid @ModelAttribute Filme filme,
                            BindingResult erros,
                            RedirectAttributes ra) {
        // Se houver erros de validação, retorna ao formulário
        if (erros.hasErrors()) {
            return "filmes/form_bootstrap";
        }
        try {
            // Tenta cadastrar o filme
            filmeService.cadastrarFilme(filme);
            ra.addFlashAttribute("success", "Filme cadastrado!"); // Mensagem de sucesso
            return "redirect:/filmes";
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "Erro ao cadastrar filme."); // Mensagem de erro
            return "filmes/form_bootstrap";
        }
    }

    // Abre o formulário de edição com os dados do filme
    @GetMapping("/editar/{id}")
    public String abrirEdicao(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("filme", filmeService.buscarFilme(id)); // Busca o filme pelo ID
            return "filmes/form_bootstrap"; // Retorna a view do formulário preenchido
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage()); // Mensagem de erro se não encontrar
            return "redirect:/filmes"; // Redireciona para a lista
        }
    }

    // Processa o envio do formulário de edição de filme
    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute Filme filme,
                            BindingResult erros,
                            RedirectAttributes ra) {
        // Se houver erros de validação, retorna ao formulário
        if (erros.hasErrors()) {
            return "filmes/form_bootstrap";
        }
        try {
            filmeService.atualizarFilme(id, filme);
            ra.addFlashAttribute("success", "Filme atualizado!");
            return "redirect:/filmes";
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "Erro ao atualizar filme.");
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
            ra.addFlashAttribute("success", "Filme excluído!"); // Mensagem de sucesso
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage()); // Mensagem de erro
        }
        return "redirect:/filmes";
    }

    //lista categorias
    @GetMapping("/filmes/novo")
    public String novoFilmeForm(Model model) {
        List<String> categorias = List.of("Drama", "Ação", "Comédia", "Suspense");
        model.addAttribute("categorias", categorias);
        model.addAttribute("filme", new Filme());
        return "formulario-filme";
    }

}
