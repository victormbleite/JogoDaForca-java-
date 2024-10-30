package br.edu.iff.jogoforca.dominio.rodada.sorteio;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;

public class RodadaSorteioFactory extends RodadaFactoryImpl{
    private static RodadaSorteioFactory soleInstance;

    public static void createSoleInstance(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        soleInstance = new RodadaSorteioFactory(repository, temaRepository, palavraRepository);
    }

    public static RodadaSorteioFactory getSoleInstance() {
        return soleInstance;
    }

    private RodadaSorteioFactory(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        super(repository, temaRepository, palavraRepository);
    }

    @Override
    public Rodada getRodada(Jogador jogador) {
        long id = getRodadaRepository().getProximoId();        
        Tema temaSorteado = sortearTema();
        Palavra[] palavrasSorteadas = sortearPalavras(temaSorteado);

        return Rodada.criar(id, palavrasSorteadas, jogador);
    }

    private Tema sortearTema() {
        Tema[] temas = getTemaRepository().getTodos();
        if (temas.length == 0) throw new IllegalCallerException("Nenhum tema cadastrado"); 
        int temaIndex = sortearIndex(temas.length);
        
        return temas[temaIndex];        
    }

    private Palavra[] sortearPalavras(Tema temaSorteado) {
        Palavra[] palavras = getPalavraRepository().getPorTema(temaSorteado);
        if (palavras.length == 0) throw new IllegalCallerException("Nenhuma palavra cadastrada para o tema " + temaSorteado.getNome());
        int quantidadeDePalavras = sortearQuantidadeDePalavras(Rodada.getMaxPalavras());
        Palavra[] palavrasSorteadas = new Palavra[quantidadeDePalavras];
        for (int i = 0; i < quantidadeDePalavras; i++) {
            int palavraIndex = sortearIndex(palavras.length);
            palavrasSorteadas[i] = palavras[palavraIndex];
        }

        return palavrasSorteadas;
    }

    private int sortearIndex(int max) {
        return (int) ((Math.random() * max) - 1);
    }

    private int sortearQuantidadeDePalavras(int max) {
        // return (int) ((Math.random() * max));
        return 1;
    }
}
