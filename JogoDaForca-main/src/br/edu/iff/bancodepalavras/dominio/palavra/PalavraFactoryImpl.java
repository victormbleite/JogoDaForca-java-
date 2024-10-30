package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.factory.EntityFactory;

public class PalavraFactoryImpl extends EntityFactory implements PalavraFactory {
    private static PalavraFactoryImpl soleInstance;

    public static void createSoleInstance(PalavraRepository repository) {
        soleInstance = new PalavraFactoryImpl(repository);
    }

    public static PalavraFactoryImpl getSoleInstance() {
        return soleInstance;
    }

    private PalavraFactoryImpl(PalavraRepository repository) {
        super(repository);
    }

    private PalavraRepository getPalavraRepository() {
        return (PalavraRepository) getRepository();
    }

    @Override
    public Palavra getPalavra(String palavra, Tema tema) {
       long id = getPalavraRepository().getProximoId();
        return Palavra.criar(id, palavra, tema);
    }
}