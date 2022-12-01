
package source;

import java.util.ArrayList;

public class Transicao {
    
    ArrayList<Integer> estadosAnalisados;
    char simboloLido;
    ArrayList<Integer> novosEstados;

    public Transicao(int estados[], char simboloLido, int novosEstados[]) {
        
        this.estadosAnalisados = new ArrayList<>();
        this.novosEstados = new ArrayList<>();
        
        for(int i = 0; i < estados.length; i++){
            this.estadosAnalisados.add(estados[i]);
        }// for
        
        this.simboloLido = simboloLido;
        
        for(int i = 0; i < novosEstados.length; i++){
            this.novosEstados.add(novosEstados[i]);
        }// for
    }// construtor
    
    public Boolean eIgual(int estadosAnalisados[], char simboloLido, int novosEstados[]) {
        for(int i = 0; i < estadosAnalisados.length; i++){
            if(!this.estadosAnalisados.contains(estadosAnalisados[i])) return false;
        }// for
        
        if(this.simboloLido != simboloLido) return false;
        
        for(int i = 0; i < novosEstados.length; i++){
            if(!this.novosEstados.contains(novosEstados[i])) return false;
        }// for
        
        return true;
    }// eIgual
    
    public String getTransicao(){
        String ret = "δ(q" + this.estadosAnalisados.get(0).toString()+ 
                "," + this.simboloLido + ",{";
        
        int qtdeEstados = this.novosEstados.size();
        for(int i = 0; i < qtdeEstados; i++){
            ret = ret + "q" + this.novosEstados.get(i).toString();
            if(i < qtdeEstados-1) ret = ret+",";
            else ret = ret + "})";
        }
        
        return ret;
                
    }
    
    public Boolean temEstadoAnalisado(int estado){
        return this.estadosAnalisados.contains(estado);
    }
    
    public Boolean temSimboloLido(int simb){
        return this.simboloLido == simb;
    }

    public ArrayList<Integer> getNovosEstados() {
        return this.novosEstados;
    }
    
    
}// transicoes
