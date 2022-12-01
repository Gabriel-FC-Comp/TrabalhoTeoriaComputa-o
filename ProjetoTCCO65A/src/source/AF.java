
package source;

public interface AF {
    
    public abstract void addSimbAlfabeto(Character novoSimb);
    public abstract void addEstado(int novoEstado, Boolean eFinal);
    public abstract void addTransicao(int estadosAnalisados[], char simboloLido, int novosEstados[]);
    
}// interface
