package br.edu.iff.bancodepalavras.dominio.letra;

public abstract class Letra {
    private char codigo;

    protected Letra(char codigo) {
        this.codigo = codigo;

    }

    public char getCodigo() {
        return codigo;
    }

    public abstract void exibir(Object contexto);

    public boolean equals(Object o) {
        // if (o == null) return false;
        // if (this == o) return true;
        // if (getClass() != o.getClass()) return false;
        
        // Letra l = (Letra) o;

        // return l.equals(o);
        if (!(o instanceof Letra)) return false;
        Letra outra = (Letra) o;
        return this.codigo == outra.codigo && this.getClass().equals(outra.getClass());
    }

    public int hashCode() {
        // int hash = 7;
        // int result = 31 * hash + codigo;
        
        // return result;
        return this.codigo + this.getClass().hashCode();
    }

    public final String toString() {
        return String.valueOf(codigo);
    }
}
