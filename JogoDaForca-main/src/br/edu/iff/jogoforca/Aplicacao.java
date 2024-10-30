package br.edu.iff.jogoforca;

import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactoryImpl;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactory;
import br.edu.iff.jogoforca.dominio.rodada.sorteio.RodadaSorteioFactory;
import br.edu.iff.jogoforca.embdr.BDRRepositoryFactory;
import br.edu.iff.jogoforca.emmemoria.MemoriaRepositoryFactory;
import br.edu.iff.jogoforca.imagem.ElementoGraficoImagemFactory;
import br.edu.iff.jogoforca.texto.ElementoGraficoTextoFactory;

public class Aplicacao {

	private static final String[] TIPOS_REPOSITORY_FACTORY = { "memoria", "relacional" };
	private static final String[] TIPOS_ELEMENTO_GRAFICO_FACTORY = { "texto", "imagem" };
	private static final String[] TIPOS_RODADA_FACTORY = { "sorteio" };

	private static Aplicacao soleInstance;

	private String tipoRepositoryFactory = TIPOS_REPOSITORY_FACTORY[0];
	private String tipoElementoGraficoFactory = TIPOS_ELEMENTO_GRAFICO_FACTORY[0];
	private String tipoRodadaFactory = TIPOS_RODADA_FACTORY[0];

	public static Aplicacao getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new Aplicacao();
		}
		return soleInstance;
	}

	private Aplicacao() {
	}

	public void configurar() {
		RodadaSorteioFactory.createSoleInstance(getRepositoryFactory().getRodadaRepository(),
				getRepositoryFactory().getTemaRepository(), getRepositoryFactory().getPalavraRepository());

		TemaFactoryImpl.createSoleInstance(getRepositoryFactory().getTemaRepository());
		PalavraFactoryImpl.createSoleInstance(getRepositoryFactory().getPalavraRepository());
		JogadorFactoryImpl.createSoleInstance(getRepositoryFactory().getJogadorRepository());

		Palavra.setLetraFactory(getLetraFactory());
		Rodada.setBonecoFactory(getBonecoFactory());

		PalavraAppService.createSoleInstance(getRepositoryFactory().getPalavraRepository(),
				getRepositoryFactory().getTemaRepository(), getPalavraFactory());

		RodadaAppService.createSoleInstance(getRepositoryFactory().getRodadaRepository(),
				getRepositoryFactory().getJogadorRepository(), getRodadaFactory());
	}

	public String[] getTiposRepositoryFactory() {
		return TIPOS_REPOSITORY_FACTORY;
	}

	public void setTipoRepositoryFactory(String tipo) {
		validarTipo(tipo, TIPOS_REPOSITORY_FACTORY, "Repository Factory");
		this.tipoRepositoryFactory = tipo;
		configurar();
	}

	public RepositoryFactory getRepositoryFactory() {
		switch (tipoRepositoryFactory) {
		case "memoria":
			return MemoriaRepositoryFactory.getSoleInstance();
		case "relacional":
			return BDRRepositoryFactory.getSoleInstance();
		default:
			throw new IllegalArgumentException("Tipo de Repository Factory inválido: " + tipoRepositoryFactory);
		}
	}

	public String[] getTiposElementoGraficoFactory() {
		return TIPOS_ELEMENTO_GRAFICO_FACTORY;
	}

	public void setTipoElementoGraficoFactory(String tipo) {
		validarTipo(tipo, TIPOS_ELEMENTO_GRAFICO_FACTORY, "Elemento Gráfico Factory");
		this.tipoElementoGraficoFactory = tipo;
		configurar();
	}

	private ElementoGraficoFactory getElementoGraficoFactory() {
		switch (tipoElementoGraficoFactory) {
		case "texto":
			return ElementoGraficoTextoFactory.getSoleInstance();
		case "imagem":
			return ElementoGraficoImagemFactory.getSoleInstance();
		default:
			throw new IllegalArgumentException(
					"Tipo de Elemento Gráfico Factory inválido: " + tipoElementoGraficoFactory);
		}
	}

	public BonecoFactory getBonecoFactory() {
		return getElementoGraficoFactory();
	}

	public LetraFactory getLetraFactory() {
		return getElementoGraficoFactory();
	}

	public String[] getTiposRodadaFactory() {
		return TIPOS_RODADA_FACTORY;
	}

	public void setTipoRodadaFactory(String tipo) {
		validarTipo(tipo, TIPOS_RODADA_FACTORY, "Rodada Factory");
		this.tipoRodadaFactory = tipo;
		configurar();
	}

	public RodadaFactory getRodadaFactory() {
		if ("sorteio".equals(tipoRodadaFactory)) {
			return RodadaSorteioFactory.getSoleInstance();
		} else {
			throw new IllegalArgumentException("Tipo de Rodada Factory inválido: " + tipoRodadaFactory);
		}
	}

	public TemaFactory getTemaFactory() {
		return TemaFactoryImpl.getSoleInstance();
	}

	public PalavraFactory getPalavraFactory() {
		return PalavraFactoryImpl.getSoleInstance();
	}

	public JogadorFactory getJogadorFactory() {
		return JogadorFactoryImpl.getSoleInstance();
	}

	private void validarTipo(String tipo, String[] tiposValidos, String nomeFactory) {
		for (String valido : tiposValidos) {
			if (valido.equals(tipo)) {
				return;
			}
		}
		throw new IllegalArgumentException(nomeFactory + " inválido: " + tipo);
	}
}
