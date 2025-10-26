package br.uel.GerenciamentoFilmesMVC.controller;

import br.uel.GerenciamentoFilmesMVC.model.Cliente;
import br.uel.GerenciamentoFilmesMVC.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

// Define como controle de clientes: Lida com requisções para gerenciar clientes"
@Controller
@RequestMapping("/clientes")
public class ClienteController {

    // Injeta automaticamente para uso nos métodos do controlador. Serviços do Cliente.
    @Autowired
    private ClienteService clienteService;

    // Mapeia requisições GET para "/clientes. Será acessado quando solicitado
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes()); // Adiciona a lista ao modelo
        return "clientes/lista_clientes"; // Retorna a view da lista
    }

    // Mapeia requisições GET para "/clientes/novo"  para formulário de cadastro
    @GetMapping("/novo")
    public String abrirCadastro(Model model) {
        model.addAttribute("cliente", new Cliente()); // Adiciona um novo cliente vazio ao modelo
        return "clientes/form_clientes";
    }

    // Mapeia requisições POST para "/clientes".
    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult erros, RedirectAttributes ra) {
        if (erros.hasErrors()) {
            return "clientes/form_clientes";
        }
        try {
            clienteService.cadastrarCliente(cliente); // Tenta cadastrar o cliente
            ra.addFlashAttribute("success", "Cliente cadastrado com sucesso!"); // Mensagem de sucesso
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Erro ao cadastrar cliente: " + e.getMessage()); // Mensagem de erro
        }
        return "redirect:/clientes";
    }

    // Mapeia requisições GET para "/clientes/editar/{id}" e abre o formulário de edição
    @GetMapping("/editar/{id}")
    public String abrirEdicao(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("cliente", clienteService.buscarCliente(id)); // Busca o cliente pelo ID
            return "clientes/form_clientes"; // Retorna a view do formulário com os dados preenchidos
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage()); // Mensagem de erro se cliente não encontrado
            return "redirect:/clientes";
        }
    }

    // Mapeia requisições PUT para "/clientes/{id}" e atualiza os dados do cliente
    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("cliente") Cliente cliente, BindingResult erros, RedirectAttributes ra) {
        if (erros.hasErrors()) {
            cliente.setId(id);
            return "clientes/form_clientes";
        }
        try {
            clienteService.atualizarCliente(id, cliente);
            ra.addFlashAttribute("success", "Cliente atualizado com sucesso!"); // Mensagem de sucesso
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Erro ao atualizar cliente: " + e.getMessage()); // Mensagem de erro
        }
        return "redirect:/clientes";
    }

    // Mapeia requisições DELETE para "/clientes/{id}" e exclui o cliente
    @DeleteMapping("/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            clienteService.excluirCliente(id); // Exclui o cliente
            ra.addFlashAttribute("success", "Cliente excluído com sucesso!"); // Mensagem de sucesso
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage()); // Mensagem de erro
        }
        return "redirect:/clientes";
    }

    // Mapeia requisições GET para "/clientes/api/pesquisa"
    @GetMapping("/api/pesquisa")
    @ResponseBody
    public List<Cliente> pesquisarApi(@RequestParam(required = false, defaultValue = "") String termo) {
        return clienteService.pesquisarClientesPorNome(termo);
    }
}
