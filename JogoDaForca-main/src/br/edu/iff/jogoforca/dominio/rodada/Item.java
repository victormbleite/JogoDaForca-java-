package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.dominio.ObjetoDominioImpl;

public class Item extends ObjetoDominioImpl {
    private Palavra palavra;
    private boolean[] posicoesDescobertas;
    private String palavraArriscada = null;

    static Item criar(int id, Palavra palavra) {
        return new Item(id, palavra);
    }

    public static Item reconstruir(int id, Palavra palavra, int[] posiscoesDescobertas, String palavraArriscada) {
        return new Item(id, palavra, posiscoesDescobertas, palavraArriscada);
    }

    private Item(int id, Palavra palavra) {
        super(id);
        if (palavra == null) {
            throw new IllegalArgumentException("Palavra do item não pode ser nula");
        }
        this.palavra = palavra;
        this.posicoesDescobertas = new boolean[palavra.getTamanho()];
        for (int i = 0; i < this.posicoesDescobertas.length; i++) {
            this.posicoesDescobertas[i] = false;
        }
    }
    private Item(int id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
        super(id);
        if (palavra == null || posicoesDescobertas == null || palavraArriscada == null) {
            throw new IllegalArgumentException("Palavra, posiçõesDescobertas e palavrasArriscadas não pode ser nula");
        }
        this.palavra = palavra;
        this.palavraArriscada = palavraArriscada;
        this.posicoesDescobertas = new boolean[posicoesDescobertas.length];
        for (int i = 0; i < this.posicoesDescobertas.length; i++) {
            this.posicoesDescobertas[i] = posicoesDescobertas[i] != 0;
        }
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public Letra[] getLetrasDescobertas() {
        int tamanho = this.palavra.getTamanho() - qtdeLetrasEncobertas();
        Letra[] letras = new Letra[tamanho];
        
        int indice_letra = 0;
        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (posicoesDescobertas[i]) {
                letras[indice_letra] = palavra.getLetra(i);
                indice_letra++;
            }
        }
        return letras;
    }

    public Letra[] getLetrasEncobertas() {
        int tamanho = qtdeLetrasEncobertas();
        Letra[] letras = new Letra[tamanho];
        int indice_letra = 0;
        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (!posicoesDescobertas[i]) {
                letras[indice_letra] = palavra.getLetra(i);
                indice_letra++;
            }
        }
        return letras;
    }

    public int qtdeLetrasEncobertas() {
        int qtde = 0;
        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (!posicoesDescobertas[i]) {
                qtde++;
            }
        }
        return qtde;
    }

    public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
        return qtdeLetrasEncobertas() * valorPorLetraEncoberta;
    }

    public boolean descobriu() {
        return this.acertou() || this.qtdeLetrasEncobertas() == 0;
    }

    public void exibir(Object contexto) {
        palavra.exibir(contexto);
    }

    public void exibirAsEncobertas(Object contexto) {
        palavra.exibir(contexto, posicoesDescobertas);
    }

    boolean tentar(char codigo) {
        int[] letrasDescobertas = palavra.tentar(codigo);
        if (letrasDescobertas.length == 0) {
            return false;
        }
        for (int i = 0; i < letrasDescobertas.length; i++) {
            posicoesDescobertas[letrasDescobertas[i]] = true;
        }
        return true;
    }

    void arriscar(String palavraArriscada) {
        if (palavraArriscada == null) {
            throw new IllegalArgumentException("Palavra arriscada não pode ser nula");
        }
        this.palavraArriscada = palavraArriscada;
    }

    public String getPalavraArriscada() {
        return palavraArriscada;
    }
    
    public boolean arriscou() {
        return this.palavraArriscada != null;
    }

    public boolean acertou() {
        if (!this.arriscou()) {
            return false;
        }
        return palavra.comparar(palavraArriscada);
    }
}
