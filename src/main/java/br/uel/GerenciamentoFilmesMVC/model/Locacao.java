package br.uel.GerenciamentoFilmesMVC.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "locacoes") // Esta anotação deve corresponder ao nome da sua tabela
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Assumindo que a chave primária da sua tabela se chama 'id'

    @NotNull(message = "O cliente é obrigatório.")
    @ManyToOne
    // ANTES ESTAVA: @JoinColumn(name = "cliente_id")
    // CORRIGIDO para o nome da coluna que aparece na imagem:
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @NotNull(message = "O filme é obrigatório.")
    @ManyToOne
    // ANTES ESTAVA: @JoinColumn(name = "filme_id")
    // CORRIGIDO para o nome da coluna correta da imagem:
    @JoinColumn(name = "id_filme")
    private Filme filme;

    @NotNull(message = "A data de locação é obrigatória.")
    // Garanta que o nome da coluna no banco seja 'data_locacao'
    @Column(name = "data_locacao")
    private LocalDate dataLocacao;

    // Garanta que o nome da coluna no banco seja 'data_devolucao'
    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    // Getters
    public Long getId() { return id; }

    public Cliente getCliente() { return cliente; }

    public Filme getFilme() { return filme; }

    public LocalDate getDataLocacao() { return dataLocacao; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }

      // Setters

    public void setId(Long id) { this.id = id; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public void setFilme(Filme filme) { this.filme = filme; }

    public void setDataLocacao(LocalDate dataLocacao) { this.dataLocacao = dataLocacao; }

    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    // Metodo toString para facilitar o log e depuração/ Problema com o banco (ainda necessário?)
    @Override
    public String toString() {
        return "Locacao{" +
                "id=" + id +
                ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
                ", filme=" + (filme != null ? filme.getNome() : "null") +
                ", dataLocacao=" + dataLocacao +
                ", dataDevolucao=" + dataDevolucao +
                '}';
    }


}

