package br.uel.GerenciamentoFilmesMVC.controller;

import br.uel.GerenciamentoFilmesMVC.model.Cliente;
import br.uel.GerenciamentoFilmesMVC.service.ClienteService;
import jakarta.servlet.http.HttpSession; // NOVO IMPORT
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Constante para a chave do filtro na sessão
    private static final String FILTRO_CLIENTE_SESSAO = "filtroClienteTermo";

    @GetMapping
    public String listar(@RequestParam(name = "termoPesquisa", required = false) String termoPesquisa,
                         HttpSession session,
                         Model model) {

        String termoAtivo;

        // Verifica se uma nova pesquisa foi submetida
        if (termoPesquisa != null) {
            // Se veio um termo da requisição (formulário de pesquisa)
            if (termoPesquisa.trim().isEmpty()) {
                session.removeAttribute(FILTRO_CLIENTE_SESSAO);
                termoAtivo = null;
            } else {
                session.setAttribute(FILTRO_CLIENTE_SESSAO, termoPesquisa);
                termoAtivo = termoPesquisa;
            }
        } else {
            termoAtivo = (String) session.getAttribute(FILTRO_CLIENTE_SESSAO);
        }

        List<Cliente> clientes;
        if (termoAtivo != null && !termoAtivo.isEmpty()) {
            clientes = clienteService.pesquisarClientesPorNome(termoAtivo);
        } else {
            clientes = clienteService.listarClientes();
        }

        // Adiciona a lista de clientes e o termo ativo ao modelo
        model.addAttribute("clientes", clientes);
        model.addAttribute("termoPesquisaAtivo", termoAtivo); // Para preencher o campo de busca

        return "clientes/lista_clientes"; // Retorna a view da lista
    }

    @GetMapping("/limpar-filtro")
    public String limparFiltro(HttpSession session) {
        session.removeAttribute(FILTRO_CLIENTE_SESSAO);
        return "redirect:/clientes";
    }


    @GetMapping("/novo")
    public String abrirCadastro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form_clientes";
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult erros, RedirectAttributes ra) {
        if (erros.hasErrors()) {
            return "clientes/form_clientes";
        }
        try {
            clienteService.cadastrarCliente(cliente);
            ra.addFlashAttribute("success", "Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Erro ao cadastrar cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String abrirEdicao(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("cliente", clienteService.buscarCliente(id));
            return "clientes/form_clientes";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/clientes";
        }
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("cliente") Cliente cliente, BindingResult erros, RedirectAttributes ra) {
        if (erros.hasErrors()) {
            cliente.setId(id);
            return "clientes/form_clientes";
        }
        try {
            clienteService.atualizarCliente(id, cliente);
            ra.addFlashAttribute("success", "Cliente atualizado com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Erro ao atualizar cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }

    @DeleteMapping("/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            clienteService.excluirCliente(id);
            ra.addFlashAttribute("success", "Cliente excluído com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/clientes";
    }

    @GetMapping("/api/pesquisa")
    @ResponseBody
    public List<Cliente> pesquisarApi(@RequestParam(required = false, defaultValue = "") String termo) {
        return clienteService.pesquisarClientesPorNome(termo);
    }
}