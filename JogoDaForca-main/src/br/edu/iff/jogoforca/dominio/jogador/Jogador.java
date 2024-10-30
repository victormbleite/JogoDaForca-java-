package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.dominio.ObjetoDominioImpl;

public class Jogador extends ObjetoDominioImpl{
    private String nome;
    private int pontuacao = 0;

    private Jogador(long id, String nome) {
        super(id);
        this.setNome(nome);
    }

    private Jogador(long id, String nome, int pontuacao) {
        super(id);
        this.setNome(nome);
        this.setPontuacao(pontuacao);
    }

    public static Jogador criar(long id, String nome) {
        return new Jogador(id, nome);
    }

    public static Jogador reconstruir(long id, String nome, int pontuacao) {
        return new Jogador(id, nome, pontuacao);
    }

    public String getNome() {
        return nome;
    }

    public final void setNome(String nome) {
        if (nome == null || nome.trim().equals("")) {
            throw new IllegalArgumentException("Nome do jogador não pode ser nulo ou vazio");
        }

        this.nome = nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public final void setPontuacao(int pontuacao) {
        if (pontuacao < 0) {
            throw new IllegalArgumentException("Pontuação do jogador não pode ser negativa");
        }

        this.pontuacao = pontuacao;
    }
}
