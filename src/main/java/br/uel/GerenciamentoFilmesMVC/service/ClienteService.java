package br.uel.GerenciamentoFilmesMVC.service;

import br.uel.GerenciamentoFilmesMVC.model.Cliente;
import br.uel.GerenciamentoFilmesMVC.repository.ClienteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll(Sort.by("nome").ascending());
    }

    public Cliente buscarCliente(Long id) {
        // É uma boa prática usar uma exceção mais específica do Spring, como EntityNotFoundException,
        // mas mantive a sua RuntimeException para consistência.
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));
    }

    public void cadastrarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public void atualizarCliente(Long id, Cliente clienteAtualizado) {
        // 1. Busca o cliente existente no banco de dados
        Cliente cliente = buscarCliente(id);

        // 2. ATUALIZA OS CAMPOS PESSOAIS
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setCpf(clienteAtualizado.getCpf());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setTelefone(clienteAtualizado.getTelefone());

        // 3. ATUALIZA OS NOVOS CAMPOS DE ENDEREÇO
        cliente.setCep(clienteAtualizado.getCep());
        cliente.setLogradouro(clienteAtualizado.getLogradouro());
        cliente.setNumero(clienteAtualizado.getNumero());
        cliente.setBairro(clienteAtualizado.getBairro());
        cliente.setCidade(clienteAtualizado.getCidade());
        cliente.setEstado(clienteAtualizado.getEstado());
        cliente.setComplemento(clienteAtualizado.getComplemento()); // Adicionado o complemento também

        // 4. Salva a entidade atualizada de volta no banco
        clienteRepository.save(cliente);
    }

    public void excluirCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com id: " + id);
        }
        clienteRepository.deleteById(id);
    }

    public List<Cliente> pesquisarClientesPorNome(String termo) {
        return clienteRepository.findByNomeContainingIgnoreCase(termo);
    }
}