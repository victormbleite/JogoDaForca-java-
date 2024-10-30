package br.edu.iff.bancodepalavras.dominio.letra.texto;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactoryImpl;

public class LetraTextoFactory extends LetraFactoryImpl{

    private static LetraTextoFactory soleinstance;

    public static LetraTextoFactory getSoleInstance() {
        if (soleinstance == null) {
            soleinstance = new LetraTextoFactory();
        }
        return soleinstance;
    }

    private LetraTextoFactory() {}

    @Override
    protected Letra criarLetra(char codigo) {
        return new LetraTexto(codigo);
    }
}
