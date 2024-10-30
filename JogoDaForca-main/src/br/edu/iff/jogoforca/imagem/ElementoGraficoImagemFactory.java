package br.edu.iff.jogoforca.imagem;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.imagem.LetraImagemFactory;
import br.edu.iff.jogoforca.ElementoGraficoFactory;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.imagem.BonecoImagemFactory;

public class ElementoGraficoImagemFactory implements ElementoGraficoFactory {
    private static ElementoGraficoImagemFactory soleInstance;
    private BonecoImagemFactory bonecoImagemFactory;
    private LetraImagemFactory letraImagemFactory;


    public static ElementoGraficoImagemFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new ElementoGraficoImagemFactory();
        }
        return soleInstance;
    }

    private ElementoGraficoImagemFactory() {
        bonecoImagemFactory = BonecoImagemFactory.getSoleInstance();
        letraImagemFactory = LetraImagemFactory.getSoleInstance();
    }

    @Override
    public Letra getLetra(char codigo) {
        return letraImagemFactory.getLetra(codigo);
    }

    @Override
    public Letra getLetraEncoberta() {
        return letraImagemFactory.getLetraEncoberta();
    }

    @Override
    public Boneco getBoneco() {
        return bonecoImagemFactory.getBoneco();
    }
}
