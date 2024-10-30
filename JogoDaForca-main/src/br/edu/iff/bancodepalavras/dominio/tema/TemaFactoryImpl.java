package br.edu.iff.bancodepalavras.dominio.tema;

import br.edu.iff.factory.EntityFactory;

public class TemaFactoryImpl extends EntityFactory implements TemaFactory{
    private static TemaFactoryImpl soleInstance;

    public static void createSoleInstance(TemaRepository temaRepository) {
        soleInstance = new TemaFactoryImpl(temaRepository);
    }

    public static TemaFactoryImpl getSoleInstance() {
        return soleInstance;
    }

    private TemaFactoryImpl(TemaRepository temaRepository) {
        super(temaRepository);
    }

    private TemaRepository getTemaRepository() {
        return (TemaRepository) getRepository();
    }

    @Override
    public Tema getTema(String nome) {
       long id = getTemaRepository().getProximoId();
        return Tema.criar(id, nome);
    }

}
