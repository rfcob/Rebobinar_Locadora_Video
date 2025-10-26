package br.uel.GerenciamentoFilmesMVC.service;

import br.uel.GerenciamentoFilmesMVC.model.Cliente;
import br.uel.GerenciamentoFilmesMVC.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // Injeta o codificador de senha (BCrypt)
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

     //Autentica um cliente usando BCrypt.
    public Cliente autenticar(String email, String senha) {

        Optional<Cliente> optCliente = clienteRepository.findByEmail(email);

        if (optCliente.isEmpty()) {
            return null;
        }

        Cliente cliente = optCliente.get();

        //Compara a senha digitada (texto puro) com o HASH salvo no banco
        if (passwordEncoder.matches(senha, cliente.getSenha())) {
            cliente.setSenha(null);
            return cliente;
        }

        // Se as senhas não batem
        return null;
    }

    //Lista todos os clientes ativos.
    public List<Cliente> listarClientes() {
        // O @Where na entidade Cliente garante que só listará clientes ativos
        return clienteRepository.findAll(Sort.by("nome").ascending());
    }

    // Busca um cliente ativo pelo ID.
    public Cliente buscarCliente(Long id) {
        // O @Where na entidade Cliente garante que só encontrará clientes ativos
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));
    }

    //Cadastra um novo cliente, CRIPTOGRAFANDO a senha.
    public void cadastrarCliente(Cliente cliente) {
        // Pega a senha (texto puro) e a transforma em HASH
        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);

        clienteRepository.save(cliente);
    }

    //Atualiza os dados de um cliente (exceto a senha)
    public void atualizarCliente(Long id, Cliente clienteAtualizado) {

        Cliente clienteDoBanco = buscarCliente(id);

        // Atualiza os campos
        clienteDoBanco.setNome(clienteAtualizado.getNome());
        clienteDoBanco.setCpf(clienteAtualizado.getCpf());
        clienteDoBanco.setEmail(clienteAtualizado.getEmail());
        clienteDoBanco.setTelefone(clienteAtualizado.getTelefone());
        clienteDoBanco.setCep(clienteAtualizado.getCep());
        clienteDoBanco.setLogradouro(clienteAtualizado.getLogradouro());
        clienteDoBanco.setNumero(clienteAtualizado.getNumero());
        clienteDoBanco.setBairro(clienteAtualizado.getBairro());
        clienteDoBanco.setCidade(clienteAtualizado.getCidade());
        clienteDoBanco.setEstado(clienteAtualizado.getEstado());
        clienteDoBanco.setComplemento(clienteAtualizado.getComplemento());

        clienteRepository.save(clienteDoBanco);
    }

     // Exclui um cliente (logicamente, via Soft Delete).
    public void excluirCliente(Long id) {
        //Verifica se o cliente existe E está ativo
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com id: " + id);
        }
        clienteRepository.deleteById(id);
    }

    public List<Cliente> pesquisarClientesPorNome(String termo) {
        return clienteRepository.findByNomeContainingIgnoreCase(termo);
    }
}