package br.uel.GerenciamentoFilmesMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controlador responsável por páginas iniciais e de navegação
@Controller
public class HomeController {

    // Adicionado: Redireciona a raiz "/" para "/index"
    @GetMapping("/")
    public String rootPage() {
        return "redirect:/index";
    }

    // Mapeia a URL "/index" - a view "index.html" será exibida
    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    // Mapeia a URL "/landing"
    // Esta será a página "protegida" para onde o login redireciona
    @GetMapping("/landing")
    public String landingPage() {
        return "landing"; //
    }
}