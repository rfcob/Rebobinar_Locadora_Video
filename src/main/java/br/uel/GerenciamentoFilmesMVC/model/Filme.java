package br.uel.GerenciamentoFilmesMVC.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "filmes")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do filme é obrigatório.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    @Column(nullable = false, length = 150)
    private String nome;

    @NotNull(message = "O ano de lançamento é obrigatório.")
    @Min(value = 1888, message = "Ano inválido para lançamento.")
    @Max(value = 2100, message = "Ano inválido para lançamento.")
    private Integer anoLancamento;

    //Faltou @NotBlank
    @Size(max = 100, message = "A categoria deve ter no máximo 100 caracteres.")
    private String categoria;

    @Min(value = 1, message = "A duração deve ser maior que 0.")
    private Integer duracao;

    @NotNull(message = "Informe se o filme venceu o Oscar.")
    private Boolean vencedorOscar;

    @Size(max = 100, message = "O nome do diretor deve ter no máximo 100 caracteres.")
    private String direcao;

    @Size(max = 500, message = "O elenco deve ter no máximo 500 caracteres.")
    @Column(length = 500)
    private String elencoPrincipal;

    //Faltou @NotBlank
    @NotNull(message = "Informe o número de cópias disponíveis.")
    @Min(value = 1, message = "Deve haver pelo menos 1 cópia disponível.")
    @Max(value = 6, message = "No máximo 6 cópias disponíveis.")
    private Integer copiasDisponiveis;

    // Getters e Setters

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

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Boolean getVencedorOscar() {
        return vencedorOscar;
    }

    public void setVencedorOscar(Boolean vencedorOscar) {
        this.vencedorOscar = vencedorOscar;
    }

    public String getDirecao() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public String getElencoPrincipal() {
        return elencoPrincipal;
    }

    public void setElencoPrincipal(String elencoPrincipal) {
        this.elencoPrincipal = elencoPrincipal;
    }

    public Integer getCopiasDisponiveis() {
        return copiasDisponiveis;
    }

    public void setCopiasDisponiveis(Integer copiasDisponiveis) {
        this.copiasDisponiveis = copiasDisponiveis;
    }
}