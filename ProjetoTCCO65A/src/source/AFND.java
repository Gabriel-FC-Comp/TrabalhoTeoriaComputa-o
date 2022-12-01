package source;

import java.util.ArrayList;

public class AFND implements AF{

    ArrayList<Character> alfabeto;
    ArrayList<Integer> estados;
    ArrayList<Transicao> transicoes;
    int estadoInicial;
    ArrayList<Integer> estadosFinais;

    public AFND() {
        this.alfabeto = new ArrayList<>();
        this.estados = new ArrayList<>();
        this.transicoes = new ArrayList<>();
        this.estadoInicial = 0;
        this.estados.add(this.estadoInicial);
        this.estadosFinais = new ArrayList<>();
    }// 

    public AFND(ArrayList<Character> alfabeto, ArrayList<Integer> estados,
            ArrayList transicoes, int estadoInicial, ArrayList<Integer> estadosFinais) {
        this.alfabeto = alfabeto;
        this.estados = estados;
        this.transicoes = transicoes;
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
    }// construtor

    @Override
    public void addSimbAlfabeto(Character novoSimb) {
        if (!this.alfabeto.contains(novoSimb)) {
            this.alfabeto.add(novoSimb);
        }// if
    }// addSimbAlfabeto

    @Override
    public void addEstado(int novoEstado, Boolean eFinal) {
        if (!this.estados.contains(novoEstado)) {
            this.estados.add(novoEstado);
        }// if

        if (eFinal && !this.estadosFinais.contains(novoEstado)) {
            this.estadosFinais.add(novoEstado);
        }// if
    }// addEstado

    @Override
    public void addTransicao(int estados[], char simboloLido, int novosEstados[]) {
        
        for(int i = 0; i < this.transicoes.size(); i++){
            if(this.transicoes.get(i).eIgual(estados, simboloLido, novosEstados)){
                System.out.println("A transição informada já existe!");
                return;
            }// if
        }// for
        this.transicoes.add(new Transicao(estados, simboloLido, novosEstados));
    }// addTransicao

    public ArrayList<Character> getAlfabeto() {
        return this.alfabeto;
    }

    public int getEstadoInicial() {
        return this.estadoInicial;
    }

    public ArrayList<Integer> getEstados() {
        return this.estados;
    }

    public ArrayList<Integer> getEstadosFinais() {
        return this.estadosFinais;
    }

    public ArrayList<Transicao> getTransicoes() {
        return this.transicoes;
    }
    
    public int[] getVetEstados(){
        int size = this.estados.size();
        int estadosAux[] = new int[size];
        
        for(int i = 0; i < size; i++){
            estadosAux[i] = this.estados.get(i);
        }
        
        return estadosAux;
    }
    
    public  ArrayList<ArrayList<ArrayList<Integer>>> tabelaTransicoesPorEstado(){
        int estadosAux[] = getVetEstados();
        int numEstados = estadosAux.length;
        int qtdeSimbolos = this.alfabeto.size();
        int qtdeTransicoes = this.transicoes.size();
        Transicao tAux;
        ArrayList<ArrayList<ArrayList<Integer>>> ret = new ArrayList<>();
        ArrayList<Integer> estadosAlcancaveis;
        int numEstadosAlcancaveis;
        
        // Para cada simbolo do alfabeto
        for(int a = 0; a < qtdeSimbolos; a++){
            
            ret.add(a, new ArrayList<>());
            
            // Para cada estado
            for(int e = 0; e < numEstados; e++){
                
                estadosAlcancaveis = new ArrayList<>();
                
                // Para cada transicao
                for(int t = 0; t < qtdeTransicoes; t++){
                    tAux = this.transicoes.get(t);
                    // Verifica se o estado analisado na transição é o mesmo
                    if(tAux.temEstadoAnalisado(estadosAux[e])){
                        
                        // Verifica se o simbolo analisado é o mesmo
                        if(tAux.temSimboloLido(this.alfabeto.get(a))){
                            
                            // Se sim, vê quantos estados é possível alcançar com ele
                            numEstadosAlcancaveis = tAux.getNovosEstados().size();
                            
                            // Para cada um dos estdos alcançaveis
                            for(int ea = 0; ea < numEstadosAlcancaveis; ea++){
                                
                                // Verifica se o estado alcançavel já foi adicionado
                                if(!estadosAlcancaveis.contains(tAux.getNovosEstados().get(ea))){
                                    
                                    // Adiciona o estado alcancavel
                                    estadosAlcancaveis.add(tAux.getNovosEstados().get(ea));
                                }// if
                            }// for
                        }// if
                    }// if
                }// for
                
                // Adiciona ao retorno, o array do estado e
                ret.get(a).add(estadosAlcancaveis);
            }// for
        }// for
        
        return ret;
    }
    
    public int getIndexEstado(int estado){
        if(this.estados.contains(estado)){
            return this.estados.indexOf(estado);
        } else return -1;
    }
    
    public void destroiAFND(){
        this.alfabeto = new ArrayList<>();
        this.estados = new ArrayList<>();
        this.transicoes = new ArrayList<>();
        this.estadoInicial = 0;
        this.estados.add(this.estadoInicial);
        this.estadosFinais = new ArrayList<>();
    }
    
}// class
