package br.uel.GerenciamentoFilmesMVC.controller;

import br.uel.GerenciamentoFilmesMVC.model.Cliente;
import br.uel.GerenciamentoFilmesMVC.model.Filme;
import br.uel.GerenciamentoFilmesMVC.model.Locacao;
import br.uel.GerenciamentoFilmesMVC.service.FilmeService;
import br.uel.GerenciamentoFilmesMVC.service.LocacaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


//Controler para o painel de controle(dashboard) da sessão cliente
@Controller
@RequestMapping("/minha-conta")
public class ClienteDashboardController {

    //Injeções automaticas para objetos de locação e filme
    @Autowired
    private LocacaoService locacaoService;

    @Autowired
    private FilmeService filmeService;

    //Mapeia requisições para sessão.
    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        //Cliente logado da sessão
        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");

        // Se não houver cliente na sessão, redireciona para o login
        if (clienteLogado == null) {
            return "redirect:/index";
        }

        List<Locacao> locacoesAtivas = locacaoService.buscarLocacoesAtivasPorCliente(clienteLogado.getId());
        LocalDate hoje = LocalDate.now();
        int prazoDevolucaoDias = 3;

        List<Locacao> locacoesAtrasadas = locacoesAtivas.stream()
                .filter(loc -> loc.getDataLocacao().plusDays(prazoDevolucaoDias).isBefore(hoje))
                .collect(Collectors.toList());

        List<Locacao> locacoesEmDia = locacoesAtivas.stream()
                .filter(loc -> !loc.getDataLocacao().plusDays(prazoDevolucaoDias).isBefore(hoje))
                .collect(Collectors.toList());

        // Buscar Filmes
        List<Filme> filmesDisponiveis = filmeService.listarFilmes(); // (Poderia ter um filtro por cópias > 0)

        // Models para a view
        model.addAttribute("cliente", clienteLogado);
        model.addAttribute("locacoesEmDia", locacoesEmDia);
        model.addAttribute("locacoesAtrasadas", locacoesAtrasadas);
        model.addAttribute("filmesDisponiveis", filmesDisponiveis);

          return "cliente/minha-conta";
    }
}