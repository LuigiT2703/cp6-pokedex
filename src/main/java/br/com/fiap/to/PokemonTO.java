package br.com.fiap.to;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class PokemonTO {
    private Long codigo;
    @NotBlank private String nome;
    @NotNull @PositiveOrZero private Double altura;
    @NotNull @PositiveOrZero private Double peso;
    @NotBlank private String categoria;
    @NotNull @PastOrPresent private LocalDate dataDeCaptura;

    public PokemonTO(){}
    public PokemonTO(Long codigo, String nome, Double altura, Double peso, String categoria, LocalDate dataDeCaptura) {
        this.codigo = codigo;
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.categoria = categoria;
        this.dataDeCaptura = dataDeCaptura;
    }

    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Double getAltura() {
        return altura;
    }
    public void setAltura(Double altura) {
        this.altura = altura;
    }
    public Double getPeso() {
        return peso;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public LocalDate getDataDeCaptura() {
        return dataDeCaptura;
    }
    public void setDataDeCaptura(LocalDate dataDeCaptura) {
        this.dataDeCaptura = dataDeCaptura;
    }

}
