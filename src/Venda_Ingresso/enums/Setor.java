package Venda_Ingresso.enums;

import Venda_Ingresso.exceptions.SetorEsgotadoException;

public enum Setor {

    AMARELO("Amarelo", 180.0, 60),
    AZUL("Azul", 100.0, 100),
    BRANCO("Branco", 60.0, 210),
    VERDE("Verde", 350.0, 30);

    private String nome;
    private double preco;
    private int quantidadeDisponivel;

    Setor(String nome, double preco, int quantidadeDisponivel) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public double getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }

    public void comprar(int quantidade)throws SetorEsgotadoException  {
        if (quantidade > quantidadeDisponivel) {
            throw new SetorEsgotadoException("Ingressos esgotados para o setor"+ nome);
            
        }
       quantidadeDisponivel = quantidadeDisponivel - quantidade; 
    }

    @Override
    public String toString() {
        return nome;
    }
}