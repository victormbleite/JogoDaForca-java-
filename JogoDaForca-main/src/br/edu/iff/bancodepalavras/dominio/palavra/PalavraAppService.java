package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;

public class PalavraAppService {

	private PalavraRepository palavraRepository;
	private TemaRepository temaRepository;
	private PalavraFactory palavraFactory;

	private static PalavraAppService soleInstance;

	public static void createSoleInstance(PalavraRepository palavraRepository, TemaRepository temaRepository,
			PalavraFactory palavraFactory) {
		if (soleInstance == null) {
			soleInstance = new PalavraAppService(palavraRepository, temaRepository, palavraFactory);
		}
	}

	public static PalavraAppService getSoleInstance() {
		if (soleInstance == null) {
			throw new IllegalStateException("A instância única deve ser criada antes de ser acessada.");
		}
		return soleInstance;
	}

	private PalavraAppService(PalavraRepository palavraRepository, TemaRepository temaRepository,
			PalavraFactory palavraFactory) {
		this.palavraRepository = palavraRepository;
		this.temaRepository = temaRepository;
		this.palavraFactory = palavraFactory;
	}

	public boolean novaPalavra(String palavra, long idTema) {
		if (this.temaRepository.getPorId(idTema) == null) {
			throw new IllegalArgumentException(
					"Tema não encontrado: o ID fornecido não corresponde a nenhum tema existente.");
		}
		if (this.palavraRepository.getPalavra(palavra) != null) {
			return true;
		} else {
			try {
				this.palavraRepository
						.inserir(this.palavraFactory.getPalavra(palavra, this.temaRepository.getPorId(idTema)));
				return true;
			} catch (RepositoryException e) {
				System.err.println("Erro ao inserir a palavra no repositório: " + e.getMessage());
				return false;
			}
		}
	}
}
