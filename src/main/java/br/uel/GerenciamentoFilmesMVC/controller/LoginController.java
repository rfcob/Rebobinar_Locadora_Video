package br.uel.GerenciamentoFilmesMVC.controller;

import br.uel.GerenciamentoFilmesMVC.model.Cliente;
import br.uel.GerenciamentoFilmesMVC.service.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

//Controle para gerenciar sessão:  login e logout
@Controller
public class LoginController {

    //injeção automática da classe/dependencia  para a lógica de negocio cliente
    @Autowired
    private ClienteService clienteService;

    //Buscar cliente e chegar papeç (ROLE), admin ou cliente
    @PostMapping("/login")
    public ResponseEntity<?> processLogin(@RequestParam String email,
                                          @RequestParam String senha,
                                          HttpSession session) {

        Cliente cliente = clienteService.autenticar(email, senha);

        if (cliente != null) {
            session.setAttribute("clienteLogado", cliente);

            //Cria um Mapa para construir a resposta JSON.
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);

            // Verifica o papel (role) do cliente para definir o redirecionamento.
            if ("ROLE_ADMIN".equals(cliente.getRole())) {
                response.put("redirectUrl", "/landing");
            } else {
                response.put("redirectUrl", "/minha-conta");
            }

            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Credenciais inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    //Invalidar sessão atual e redirecionar para a index
    @GetMapping("/logout")
    public String processLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}