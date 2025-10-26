package br.uel.GerenciamentoFilmesMVC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration; // <-- 1. ADICIONE ESTE IMPORT

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // <-- 2. ADICIONE O (exclude=...)
public class GerenciamentoFilmesMVCAplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoFilmesMVCApplication.class, args);
    }
}