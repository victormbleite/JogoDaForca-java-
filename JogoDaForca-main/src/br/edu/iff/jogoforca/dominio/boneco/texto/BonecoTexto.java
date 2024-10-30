package br.edu.iff.jogoforca.dominio.boneco.texto;

import java.util.HashMap;

import br.edu.iff.jogoforca.dominio.boneco.Boneco;

public class BonecoTexto implements Boneco{
    private static BonecoTexto soleInstance;
    private HashMap<Integer, String> partesDoCorpoErros = new HashMap<>();
    
    
    
    private BonecoTexto() {
        partesDoCorpoErros.put(0, "sem erros ainda");
        partesDoCorpoErros.put(1, "cabeça");
        partesDoCorpoErros.put(2, "cabeça, olho esquerdo");
        partesDoCorpoErros.put(3, "cabeça, olho esquerdo, olho direito");
        partesDoCorpoErros.put(4, "cabeça, olho esquerdo, olho direito, nariz");
        partesDoCorpoErros.put(5, "cabeça, olho esquerdo, olho direito, nariz, boca");
        partesDoCorpoErros.put(6, "cabeça, olho esquerdo, olho direito, nariz, boca, tronco");
        partesDoCorpoErros.put(7, "cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo");
        partesDoCorpoErros.put(8, "cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito");
        partesDoCorpoErros.put(9, "cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda");
        partesDoCorpoErros.put(10, "cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda, perna direita");
    }
    
    public static BonecoTexto getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoTexto();
        }

        return soleInstance;
    }

    @Override
    public void exibir(Object contexto, int partes) {
        System.out.println(partesDoCorpoErros.get(partes));
    }
}
