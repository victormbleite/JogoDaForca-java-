package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;

import java.util.ArrayList;


public class Rodada extends ObjetoDominioImpl{
    private static int maxPalavras = 3;
    private static int maxErros = 10;
    private static int pontosQuandoDescobreTodasAsPalavras = 100;
    private static int pontosPorLetraEncoberta = 15;
    private static BonecoFactory bonecoFactory;
    private Boneco boneco;
    private Jogador jogador;
    private Item[] itens;
    private ArrayList<Letra> erradas = new ArrayList<Letra>();
    private ArrayList<Letra> certas = new ArrayList<Letra>();
    private ArrayList<Letra> tentativas = new ArrayList<Letra>();

    public static int getMaxPalavras() {
        return Rodada.maxPalavras;
    }

    public static void setMaxPalavras(int maxPalavras) {
        Rodada.maxPalavras = maxPalavras;
    }

    public static int getMaxErros() {
        return Rodada.maxErros;
    }

    public static void setMaxErros(int max) {
        Rodada.maxErros = max;
    }

    public static int getPontosQuandoDescobreTodasAsPalavras() {
        return Rodada.pontosQuandoDescobreTodasAsPalavras;
    }

    public static void setPontosQuandoDescobreTodasAsPalavras(int pontos) {
        Rodada.pontosQuandoDescobreTodasAsPalavras = pontos;
    }

    public static int getPontosPorLetraEncoberta() {
        return Rodada.pontosPorLetraEncoberta;
    }

    public static void setPontosPorLetraEncoberta(int pontos) {
        Rodada.pontosPorLetraEncoberta = pontos;
    }

    public static void setBonecoFactory(BonecoFactory b) {
        if (b == null) {
            throw new IllegalArgumentException("Fábrica de bonecos não pode ser nula");
        }
        Rodada.bonecoFactory = b;
    }

    public static BonecoFactory getBonecoFactory() {
        return Rodada.bonecoFactory;
    }

    public static Rodada criar(long id, Palavra[] palavras, Jogador jogador) {
        return new Rodada(id, palavras, jogador);
    }

    public static Rodada reconstituir(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
        return new Rodada(id, itens, erradas, jogador);
    }

    private Rodada(long id, Palavra[] palavras, Jogador jogador) {
        super(id);
        if (Rodada.bonecoFactory == null) throw new IllegalStateException("Fábrica de bonecos não foi definida");
        this.boneco = Rodada.bonecoFactory.getBoneco();
        if (jogador == null) throw new IllegalArgumentException("Jogador não pode ser nulo");
        this.jogador = jogador;
        if (palavras == null) throw new IllegalArgumentException("Palavras não podem ser nulas");
        if (palavras.length == 0) throw new IllegalArgumentException("Deve haver pelo menos uma palavra");
        this.itens = new Item[palavras.length];
        for (int i = 0; i < palavras.length; i++) {
            int idAleatorio = (int) (Math.random() * 1000);
            this.itens[i] = Item.criar(idAleatorio, palavras[i]);
        }

    }

    private Rodada(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
        super(id);
        if (Rodada.bonecoFactory == null) throw new IllegalStateException("Fábrica de bonecos não foi definida");
        this.boneco = Rodada.bonecoFactory.getBoneco();
        if (jogador == null) throw new IllegalArgumentException("Jogador não pode ser nulo");
        this.jogador = jogador;
        if (itens == null) throw new IllegalArgumentException("Itens não podem ser nulos");
        if (itens.length == 0) throw new IllegalArgumentException("Deve haver pelo menos um item");
        this.itens = itens;
        if (erradas == null) throw new IllegalArgumentException("Letras erradas não podem ser nulas");
        if (erradas.length > getMaxErros()) throw new IllegalArgumentException("Número de letras erradas maior que o permitido");
        for (int i = 0; i < erradas.length; i++) {
            this.erradas.add(erradas[i]);
        }
    }

    public Jogador getJogador() {
        return this.jogador;
    }

    public Tema getTema() {
        return this.itens[0].getPalavra().getTema();
    }

    public Palavra[] getPalavras() {
        Palavra[] palavras = new Palavra[this.itens.length];
        for (int i = 0; i < this.itens.length; i++) {
            palavras[i] = this.itens[i].getPalavra();
        }
        return palavras;
    }

    public int getNumPalavras() {
        return this.itens.length;
    }

    public void tentar(char codigo) {
        if (jaTentouLetra(codigo)) {
            System.out.println("Você já tentou essa letra");
            return;
        }
        LetraFactory letraFactory = Palavra.getLetraFactory();
        boolean acertouEmAlgumaPalavra = false;
        Letra letra = letraFactory.getLetra(codigo);
        for (int i = 0; i < this.itens.length; i++) {
            this.tentativas.add(letra);

            boolean acertou = this.itens[i].tentar(codigo);
            if (!acertou && !acertouEmAlgumaPalavra) {
                this.erradas.add(letra);
            }
            else {
                acertouEmAlgumaPalavra = true;
                certas.add(letra);
            }
        }

    }

    public void arriscar(String[] palavras) {
        if (palavras == null) throw new IllegalArgumentException("Palavras não podem ser nulas");
        if (palavras.length != this.itens.length) throw new IllegalArgumentException("Número de palavras diferente do número de palavras da rodada");
        for (int i = 0; i < this.itens.length; i++) {
            this.itens[i].arriscar(palavras[i]);
        }
    }

    public void exibirItens(Object contexto) {
        for (int i = 0; i < this.itens.length; i++) {
            this.itens[i].exibirAsEncobertas(contexto);
        }
    }

    public void exibirBoneco(Object contexto) {
        this.boneco.exibir(contexto, getQdeErros());
    }

    public void exibirPalavras(Object contexto) {
        for (int i = 0; i < this.itens.length; i++) {
            this.itens[i].exibir(contexto);
        }
    }

    public void exibirLetrasErradas(Object contexto) {
        for (int i = 0; i < this.erradas.size(); i++) {
            this.erradas.get(i).exibir(contexto);
        }
    }

    public Letra[] getTentativas() {
        Letra[] letras = new Letra[this.tentativas.size()];
        for (int i = 0; i < this.tentativas.size(); i++) {
            letras[i] = this.tentativas.get(i);
        }
        return letras;
    }

    public boolean jaTentouLetra(char codigo) {
        Letra[] tentadas = getTentativas();
        for (int i = 0; i < tentadas.length; i++) {
            if (tentadas[i].getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    public Letra[] getCertas() {
        Letra[] letras = new Letra[this.certas.size()];
        for (int i = 0; i < this.certas.size(); i++) {
            letras[i] = this.certas.get(i);
        }
        return letras;
    }
    
    public Letra[] getErradas() {
        Letra[] letras = new Letra[this.erradas.size()];
        for (int i = 0; i < this.erradas.size(); i++) {
            letras[i] = this.erradas.get(i);
        }
        return letras;
    }

    public int calcularPontos() {
        if (descobriu()) {
            int pontosTotal = Rodada.getPontosQuandoDescobreTodasAsPalavras();
            int pontosPorLetraEncoberta = Rodada.getPontosPorLetraEncoberta();

            for (int i = 0; i < this.itens.length; i++) {
                pontosTotal += this.itens[i].calcularPontosLetrasEncobertas(pontosPorLetraEncoberta);
            }
            return pontosTotal;
        } 
        else {
            return 0;
        }
    }

    public boolean encerrou() {
        return arriscou() || descobriu() || getQdeErros() >= Rodada.getMaxErros();
    }

    public boolean descobriu() {
        for (int i = 0; i < this.itens.length; i++) {
            if (!this.itens[i].descobriu()) {
                return false;
            }
        }
        return true;
    }

    public boolean arriscou() {
        for (int i = 0; i < this.itens.length; i++) {
            if (this.itens[i].arriscou()) {
                return true;
            }
        }
        return false;
    }

    public int getQtdeTentativasRestantes() {
        return Rodada.getMaxErros() - getQdeErros();
    }
    
    public int getQdeErros() {
        return getErradas().length;
    }

    public int getQtdeAcertos() {
        return getCertas().length;
    }

    public int getQtdeTentativas() {
        return getTentativas().length;
    }
}
