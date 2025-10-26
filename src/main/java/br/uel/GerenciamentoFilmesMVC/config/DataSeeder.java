package br.uel.GerenciamentoFilmesMVC.config;

import br.uel.GerenciamentoFilmesMVC.model.Cliente;
import br.uel.GerenciamentoFilmesMVC.repository.ClienteRepository;
// Imports novos/modificados
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // NOVO IMPORT
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    // Injeta o PasswordEncoder diretamente
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        String adminEmail = "admin@locadora.com";
        String adminCpf = "00000000000"; // CPF fictício

        //Verifica se o usuário já existe (por email OU cpf)
        if (clienteRepository.findByEmail(adminEmail).isEmpty() &&
                clienteRepository.findByCpf(adminCpf).isEmpty()) { // MODIFICADO: Adicionado findByCpf

            System.out.println(">>> [DataSeeder] Criando cliente administrador de teste...");

            // e não existir, cria um novo objeto Cliente
            Cliente admin = new Cliente();
            admin.setNome("Admin da Locadora");
            admin.setEmail(adminEmail);

            // CRIPTOGRAFA a senha aqui mesmo
            admin.setSenha(passwordEncoder.encode("admin123"));

            // Dados de endereço e pessoais (obrigatórios do seu banco)
            admin.setCpf(adminCpf);
            admin.setCep("00000-000");
            admin.setLogradouro("Rua Admin");
            admin.setNumero("1");
            admin.setBairro("Centro");
            admin.setCidade("LocadoraCity");
            admin.setEstado("PR");
            admin.setAtivo(true); // Garante que ele está ativo

            clienteRepository.save(admin);

            System.out.println(">>> [DataSeeder] Cliente administrador criado com sucesso!");
        } else {
            System.out.println(">>> [DataSeeder] Cliente administrador já existe. Nenhuma ação necessária.");
        }
    }
}