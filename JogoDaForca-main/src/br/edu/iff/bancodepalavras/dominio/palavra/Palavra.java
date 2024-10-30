package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;

import java.util.ArrayList;

public class Palavra extends ObjetoDominioImpl {
	private static LetraFactory letraFactory;
	private Tema tema;
	private Letra[] letras;
	private Letra encoberta;

	public static void setLetraFactory(LetraFactory l) {
		if (l == null) {
			throw new IllegalArgumentException("Fábrica de letras não pode ser nula");
		}
		letraFactory = l;
	}

	public static LetraFactory getLetraFactory() {
		return letraFactory;
	}

	public static Palavra criar(long id, String palavra, Tema tema) {
		return new Palavra(id, palavra, tema);
	}

	public static Palavra reconstruir(long id, String palavra, Tema tema) {
		return new Palavra(id, palavra, tema);
	}

	private Palavra(long id, String palavra, Tema tema) {
		super(id);
		if (tema == null) {
			throw new IllegalArgumentException("Tema da palavra não pode ser nulo");
		}
		this.tema = tema;

		if (Palavra.letraFactory == null) {
			throw new IllegalArgumentException("Defina fabrica de letras antes de criar Palavra");
		}

		this.letras = new Letra[palavra.length()];
		LetraFactory letraFactory = Palavra.getLetraFactory();
		this.encoberta = letraFactory.getLetraEncoberta();

		for (int i = 0; i < palavra.length(); i++) {
			char c = palavra.charAt(i);
			Letra l = letraFactory.getLetra(c);
			this.letras[i] = l;
		}
	}

	public Letra[] getLetras() {
		return letras.clone();
	}

	public Letra getLetra(int posicao) {
		if (posicao < 0 || posicao >= letras.length) {
			throw new IllegalArgumentException("Posição de letra inválida");
		}
		return letras[posicao];
	}

	public void exibir(Object contexto) {
		System.out.println(this.toString());
	}

	public void exibir(Object contexto, boolean[] posicoes) {
		if (posicoes.length != letras.length) {
			throw new IllegalArgumentException("Vetor de posições tem tamanho diferente da palavra");
		}

		String s = "";
		for (int i = 0; i < letras.length; i++) {
			if (posicoes[i] == true) {
				s += letras[i].getCodigo();
			} else {
				s += encoberta.getCodigo();
			}
		}
		System.out.println(s);
	}

	public int[] tentar(char codigo) {
		ArrayList<Integer> posicoesList = new ArrayList<Integer>();
		for (int i = 0; i < letras.length; i++) {
			if (letras[i].getCodigo() == codigo) {
				posicoesList.add(i);
			}
		}
		int[] posicoes = new int[posicoesList.size()];
		for (int i = 0; i < posicoesList.size(); i++) {
			posicoes[i] = posicoesList.get(i);
		}
		return posicoes;
	}

	public Tema getTema() {
		return tema;
	}

	public boolean comparar(String palavra) {
		return this.toString().equals(palavra);
	}

	public int getTamanho() {
		return letras.length;
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < getTamanho(); i++) {
			s += letras[i].getCodigo();
		}
		return s;
	}
}
