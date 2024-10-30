package br.edu.iff.bancodepalavras.dominio.letra.texto;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;

public class LetraTexto extends Letra {
    public LetraTexto(char codigo) {
        super(codigo);
    }

    public void exibir(Object contexto) {
        System.out.print(getCodigo());
    }
}
