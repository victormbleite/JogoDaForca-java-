package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.factory.EntityFactory;

public class JogadorFactoryImpl extends EntityFactory implements JogadorFactory  {
    private static JogadorFactoryImpl soleInstance;

    public static void createSoleInstance(JogadorRepository repository) {
        soleInstance = new JogadorFactoryImpl(repository);
    }
    
    public static JogadorFactoryImpl getSoleInstance() {
       return soleInstance;
    }

    private JogadorFactoryImpl(JogadorRepository repository) {
        super(repository);
    }

    private JogadorRepository getJogadorRepository() {
        return (JogadorRepository) getRepository();
    }

    public Jogador getJogador(String nome) {
        long id = getJogadorRepository().getProximoId();
        return Jogador.criar(id, nome);
    }
}
