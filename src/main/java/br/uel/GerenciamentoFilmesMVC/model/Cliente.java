package br.uel.GerenciamentoFilmesMVC.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SQLDelete(sql = "UPDATE clientes SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Atributos de Dados Pessoais ---
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11)
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 4, message = "A senha deve ter no mínimo 4 caracteres.")
    @Column(nullable = false, length = 255) // Tamanho para hash
    private String senha;

    @Size(max = 20)
    private String telefone;

    // --- Atributos endereço ---
    @NotBlank(message = "O CEP é obrigatório.")
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório.")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String logradouro;

    @NotBlank(message = "O número é obrigatório.")
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    private String numero;

    @Size(max = 100)
    @Column(length = 100)
    private String complemento;

    @NotBlank(message = "O bairro é obrigatório.")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String cidade;

    @NotBlank(message = "O estado (UF) é obrigatório.")
    @Size(min = 2, max = 2)
    @Column(nullable = false, length = 2)
    private String estado;

    //Papel e atividade
    //começa ativo - todos
    @Column(name = "ativo")
    private boolean ativo = true; //

    //Defini o papel cliente
    @Column(nullable = false, length = 20)
    private String role = "ROLE_CLIENTE"; // Padrão

    // --- Getters e Setters ---

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}