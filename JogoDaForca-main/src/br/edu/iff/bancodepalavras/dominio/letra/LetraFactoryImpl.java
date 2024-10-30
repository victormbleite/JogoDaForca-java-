package br.edu.iff.bancodepalavras.dominio.letra;

import java.util.HashMap;

public abstract class LetraFactoryImpl implements LetraFactory{
    private Letra encoberta;
    private char codigoEncoberta = '_';
    private HashMap<Character, Letra> pool = new HashMap<Character, Letra>();


    protected LetraFactoryImpl() {}

    @Override
    final public Letra getLetra(char codigo) {
        if (codigo < 'a' || codigo > 'z') {
            throw new IllegalArgumentException("Letra fora do intervalo a-z");
        }
        if (!this.pool.containsKey(codigo)) {
            Letra l = this.criarLetra(codigo);
            this.pool.put(codigo, l);
        }
        return this.pool.get(codigo);
    }

    @Override
    final public Letra getLetraEncoberta() {
        if (this.encoberta == null) {
            this.encoberta = this.criarLetra(this.codigoEncoberta);
        }
        return this.encoberta;
    }
    protected abstract Letra criarLetra(char codigo);

}
