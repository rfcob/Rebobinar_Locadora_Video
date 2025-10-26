package br.uel.GerenciamentoFilmesMVC.controller;

import br.uel.GerenciamentoFilmesMVC.dto.LocacaoDTO;
import br.uel.GerenciamentoFilmesMVC.model.Locacao;
import br.uel.GerenciamentoFilmesMVC.service.ClienteService;
import br.uel.GerenciamentoFilmesMVC.service.FilmeService;
import br.uel.GerenciamentoFilmesMVC.service.LocacaoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;


// Gerenciar locações
@Controller
@RequestMapping("/locacoes")
public class LocacaoController {

    //  mensagens de erro ou informação no console
    private static final Logger log = LoggerFactory.getLogger(LocacaoController.class);

    // Injeta os serviços
    @Autowired
    private LocacaoService locacaoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private FilmeService filmeService;


    // Exibe a lista de todas as locações registradas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("locacoes", locacaoService.listarLocacoes());
        return "locacao/lista_locacao"; // Retorna a view com a lista
    }

    // Abre o formulário para registrar uma nova locação
    //Deveria ter a logica na Service - corrigir
    @GetMapping("/nova")
    public String abrirFormulario(Model model) {
        model.addAttribute("locacaoDto", new LocacaoDTO());
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("filmes", filmeService.listarFilmes());
        return "locacao/form_locacao";
    }

    // Processa o envio do formulário de locação
    //A lógica deve ser passada para a Service
    @PostMapping
    public String salvar(@Valid @ModelAttribute("locacaoDto") LocacaoDTO locacaoDto,
                         BindingResult erros,
                         RedirectAttributes ra,
                         Model model) {
        // Se houver erros de validação, retorna ao formulário com os dados necessários
        if (erros.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarClientes());
            model.addAttribute("filmes", filmeService.listarFilmes());
            return "locacao/form_locacao";
        }
        try {
            locacaoService.cadastrarMultiplasLocacoes(locacaoDto);
            ra.addFlashAttribute("success", "Locações registradas com sucesso!");
            return "redirect:/locacoes";
        } catch (Exception e) {
            log.error("Erro ao tentar salvar locações", e);
            ra.addFlashAttribute("error", "Erro ao registrar locações: " + e.getMessage());
            return "redirect:/locacoes/nova";
        }
    }

    // Exclui uma locação pelo ID
    //Lógica deve ser passada para a Service
    @DeleteMapping("/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            locacaoService.excluirLocacao(id);
            ra.addFlashAttribute("success", "Locação excluída com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/locacoes";
    }

    // Abre o formulário para devolução de filmes
    @GetMapping("/devolucao")
    public String abrirFormularioDevolucao(Model model) {
        model.addAttribute("resultados", List.of());
        return "locacao/form_devolucao";
    }

    // Busca locações pendentes de devolução
    @PostMapping("/devolucao/buscar")
    public String buscarLocacoesParaDevolucao(@RequestParam(name = "termoBusca", required = false) String termo,
                                              Model model) {
        List<Locacao> resultados = locacaoService.pesquisarLocacoesPendentes(termo);
        model.addAttribute("resultados", resultados);
        model.addAttribute("termoBusca", termo);
        return "locacao/form_devolucao";
    }

    // Registra a devolução de um filme por ID
    @GetMapping("/{id}/devolver")
    public String devolverFilme(@PathVariable Long id, RedirectAttributes ra) {
        try {
            locacaoService.registrarDevolucao(id);
            ra.addFlashAttribute("success", "Filme devolvido com sucesso!");
        } catch (RuntimeException e) {
            log.error("Erro ao tentar registrar devolução para o ID " + id, e);
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/locacoes";
    }
}