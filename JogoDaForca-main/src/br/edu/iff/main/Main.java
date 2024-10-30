package br.edu.iff.main;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.repository.RepositoryException;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Aplicacao aplicacao = Aplicacao.getSoleInstance();

    public static void main(String[] args) {
        try {
            // Configurar a aplicação
            configurarAplicacao();
            
            // Inicializar serviços e repositórios
            PalavraAppService palavraAppService = PalavraAppService.getSoleInstance();
            TemaRepository temaRepository = aplicacao.getRepositoryFactory().getTemaRepository();
            TemaFactory temaFactory = aplicacao.getTemaFactory();

            // Inserir temas
            inserirTemas(temaRepository, temaFactory);
            
            // Inserir palavras
            inserirPalavras(palavraAppService);

            // Exibir boas-vindas
            exibirBoasVindas();

            // Criar jogador
            Jogador jogador = criarJogador();
            
            // Jogar a rodada
            jogarRodada(jogador);
            // testarRodada(jogador);
        } catch (RepositoryException e) {
            System.err.println("Erro ao acessar o repositório: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Exibir stack trace para outros erros
        }
    }

    private static void configurarAplicacao() {

        aplicacao.configurar();
    }

    private static void inserirTemas(TemaRepository temaRepository, TemaFactory temaFactory) throws RepositoryException {
        String[] temas = {"Animais", "Filmes", "Linguagens_Programação", "Esportes"};
        for (String tema : temas) {
            temaRepository.inserir(temaFactory.getTema(tema));
        }
    }

    private static void inserirPalavras(PalavraAppService palavraAppService) {
        String[][] palavras = {
            {"elefante", "tigre", "golfinho"},
            {"matrix", "avatar", "vingadores"},
            {"javascript", "java", "python"},
            {"futebol", "basquete", "tenis"}
        };
        for (int i = 0; i < palavras.length; i++) {
            for (String palavra : palavras[i]) {
            	 try {
                     palavraAppService.novaPalavra(palavra, i + 1);
                 } catch (IllegalArgumentException e) {
                     System.err.println("Erro ao inserir a palavra: " + palavra + " - " + e.getMessage());
                 }
             }
         }
     }

    private static Jogador criarJogador() {
        System.out.print("Digite seu nome: ");
        String nomeJogador = scanner.next();
        System.out.println("\nBem-vindo, " + nomeJogador + "! Prepare-se para o desafio.");

        Jogador jogador = aplicacao.getJogadorFactory().getJogador(nomeJogador);
        try {
            aplicacao.getRepositoryFactory().getJogadorRepository().inserir(jogador);
        } catch (RepositoryException e) {
            throw new RuntimeException("Erro ao inserir jogador!", e);
        }
        return jogador;
    }

    private static void exibirBoasVindas() {
        System.out.println("###############################################");
        System.out.println("#                                             #");
        System.out.println("#       Bem-vindo ao Jogo da Forca 2.0!       #");
        System.out.println("#   Teste seus conhecimentos e divirta-se!    #");
        System.out.println("#                                             #");
        System.out.println("###############################################\n");
    }

    private static void jogarRodada(Jogador jogador) {
        RodadaAppService rodadaAppService = RodadaAppService.getSoleInstance();
        Rodada rodada = rodadaAppService.novaRodada(jogador);
        
        while (!rodada.encerrou()) {
            System.out.println("\nTentativas restantes: " + rodada.getQtdeTentativasRestantes());
            System.out.println("Letras já tentadas: ");
            for (Letra tentativa : rodada.getTentativas()) {
                tentativa.exibir(null);
                System.out.print(" ");
            }
            System.out.println();
            
            System.out.println("O tema escolhido foi: " + rodada.getTema().getNome());
            System.out.print("Palavras:");
            rodada.exibirItens(null);
            System.out.println("get erradas: ");
            for (Letra errada : rodada.getErradas()) {
                errada.exibir(null);
                System.out.print(" ");
            }
            System.out.println();
            System.out.println("get certas: ");
            for (Letra certa : rodada.getCertas()) {
                certa.exibir(null);
                System.out.print(" ");
            }
            System.out.println();
            System.out.println();
            System.out.println("Estado atual do boneco:");
            rodada.exibirBoneco(null);
            System.out.println();

            System.out.println("(1) Tentar adivinhar uma letra");
            System.out.println("(2) Arriscar a(s) palavra(s)");
            System.out.print("Escolha: ");
            String escolha = scanner.next();

            switch (escolha) {
                case "1":
                    System.out.print("Digite uma letra: ");
                    rodada.tentar(scanner.next().charAt(0));
                    break;
                case "2":
                    String[] palavrasArriscadas = new String[rodada.getNumPalavras()];
                    for (int i = 0; i < palavrasArriscadas.length; i++) {
                        System.out.print("Digite sua tentativa para a palavra " + (i + 1) + ": ");
                        palavrasArriscadas[i] = scanner.next();
                    }
                    rodada.arriscar(palavrasArriscadas);
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        if (rodada.descobriu()) {
            System.out.println("Parabéns! Você acertou a palavra!");
        } else {
            System.out.println("Você perdeu! Mais sorte na próxima rodada!");
        }
        System.out.println("Sua pontuação final: " + rodada.calcularPontos());
    }

    // private static void testarRodada(Jogador jogador) {
    //     RodadaAppService rodadaAppService = RodadaAppService.getSoleInstance();
    //     Rodada rodada = rodadaAppService.novaRodada(jogador);

    //     System.out.println("O tema escolhido foi: " + rodada.getTema().getNome());
    //     Palavra[] palavras = rodada.getPalavras();
    //     System.out.println(rodada.getNumPalavras());
    //     for (int i = 0; i < palavras.length; i++) {
    //         palavras[i].exibir(null);
    //     }
    //     rodada.exibirBoneco(null);
        // rodada.exibirItens(null);
        // rodada.exibirPalavras(null);
        // rodada.exibirLetrasErradas(null);
        // rodada.getTentativas();
        // rodada.exibirPalavras(null);
        
    // }
}

