package br.uel.GerenciamentoFilmesMVC.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//Entidade clientes - claase cliente, seus atibutos e especificações
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos de Dados Pessoais

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 caracteres.")
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Email(message = "Formato de e-mail inválido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    private String email;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
    private String telefone;

    // Atributos endereço


    @NotBlank(message = "O CEP é obrigatório.")
    @Size(max = 10, message = "O CEP deve ter no máximo 10 caracteres.")
    @Column(nullable = false, length = 10)
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório.")
    @Size(max = 100, message = "O logradouro deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String logradouro;

    @NotBlank(message = "O número é obrigatório.")
    @Size(max = 10, message = "O número deve ter no máximo 10 caracteres.")
    @Column(nullable = false, length = 10)
    private String numero;

    @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres.")
    @Column(length = 100)
    private String complemento;

    @NotBlank(message = "O bairro é obrigatório.")
    @Size(max = 50, message = "O bairro deve ter no máximo 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    @Size(max = 50, message = "A cidade deve ter no máximo 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String cidade;

    @NotBlank(message = "O estado (UF) é obrigatório.")
    @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres.")
    @Column(nullable = false, length = 2)
    private String estado;



    // Getters


    public Long getId() { return id; }

    public String getNome() { return nome; }

    public String getCpf() { return cpf; }

    public String getEmail() { return email; }

    public String getTelefone() { return telefone; }

    public String getCep() { return cep; }

    public String getLogradouro() { return logradouro; }

    public String getNumero() { return numero; }

    public String getComplemento() { return complemento; }

    public String getBairro() { return bairro; }

    public String getCidade() { return cidade; }


    public String getEstado() { return estado; }


  // Setters

    public void setId(Long id) { this.id = id; }

    public void setNome(String nome) { this.nome = nome; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public void setEmail(String email) { this.email = email; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public void setCep(String cep) { this.cep = cep; }

    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public void setNumero(String numero) { this.numero = numero; }

    public void setComplemento(String complemento) { this.complemento = complemento; }

    public void setBairro(String bairro) { this.bairro = bairro; }

    public void setCidade(String cidade) { this.cidade = cidade; }

    public void setEstado(String estado) { this.estado = estado; }
}