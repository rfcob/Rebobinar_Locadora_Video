package br.uel.GerenciamentoFilmesMVC.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean; // NOVO IMPORT
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // NOVO IMPORT
import org.springframework.security.crypto.password.PasswordEncoder; // NOVO IMPORT
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AutorizacaoInterceptor autorizacaoInterceptor;

    /**
     * codificador de senha (BCrypt) como um Bean
     * para que o Spring possa injetá-lo no ClienteService.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(autorizacaoInterceptor)
                .addPathPatterns("/**") // Aplica a todas as rotas
                .excludePathPatterns(
                        "/",         // Libera a raiz
                        "/index",    // Libera a página inicial (onde está o modal)
                        "/login",    // Libera o POST de login
                        "/css/**",   // Libera arquivos estáticos
                        "/js/**",
                        "/img/**",
                        "/error"
                );
    }
}