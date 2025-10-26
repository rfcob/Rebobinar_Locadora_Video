package br.uel.GerenciamentoFilmesMVC.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

// Classe DTO (Data Transfer Object) usada para transportar dados da locação entre a camada de
// apresentação e a lógica de negócio
public class LocacaoDTO {

    // ID do cliente
    // Campo obrigatório: não pode ser nulo
    @NotNull(message = "O cliente é obrigatório.")
    private Long clienteId;

    // Lista de IDs dos filmes selecionados para locação
    // Campo obrigatório: deve conter ao menos um filme
    @NotEmpty(message = "Selecione ao menos um filme.")
    private List<Long> filmeIds;

    // Data em que a locação foi realizada
    // Campo obrigatório e formatado como "yyyy-MM-dd" para compatibilidade com formulários HTML
    @NotNull(message = "A data de locação é obrigatória.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataLocacao;

    // Data prevista para devolução dos filmes
    // Campo opcional: não possui @NotNull, permitindo que seja deixado em branco
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDevolucao;

    // Getters

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<Long> getFilmeIds() {
        return filmeIds;
    }

    public void setFilmeIds(List<Long> filmeIds) {
        this.filmeIds = filmeIds;
    }

    public LocalDate getDataLocacao() {
        return dataLocacao;
    }

    //Setters


    public void setDataLocacao(LocalDate dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
