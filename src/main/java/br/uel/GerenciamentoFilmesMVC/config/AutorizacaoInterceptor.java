package br.uel.GerenciamentoFilmesMVC.config;

import br.uel.GerenciamentoFilmesMVC.model.Cliente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class AutorizacaoInterceptor implements HandlerInterceptor {

    private static final List<String> CAMINHOS_ADMIN = List.of(
            "/clientes",
            "/filmes",
            "/locacoes"
    );

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        if (session == null || session.getAttribute("clienteLogado") == null) {
            response.sendRedirect("/index");
            return false;
        }

        Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
        String role = cliente.getRole();

        boolean ehCaminhoAdmin = false;
        for (String path : CAMINHOS_ADMIN) {
            // Verifica se a URI começa com algum caminho de admin OU é EXATAMENTE /landing
            // (Considerando que /landing agora pode ser a página inicial do admin)
            if (uri.startsWith(path) || uri.equals("/landing")) {
                ehCaminhoAdmin = true;
                break;
            }
        }

        if (ehCaminhoAdmin) {
            if ("ROLE_ADMIN".equals(role)) {
                return true;
            } else {
                // --- MUDANÇA AQUI ---
                // Cliente tentando acessar área restrita -> redireciona para o dashboard DELE
                response.sendRedirect("/minha-conta");
                // --- FIM DA MUDANÇA ---
                return false;
            }
        }

        // Se NÃO for um caminho de admin (ex: /minha-conta, /catalogo, etc.)
        // E o usuário for um CLIENTE, permite o acesso.
        // (Admins também podem acessar essas áreas)
        return true;
    }
}