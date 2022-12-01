package source;

import java.util.ArrayList;

public class AFD implements AF{
    
    ArrayList<Character> alfabeto;
    ArrayList<Integer> estados;
    ArrayList<Transicao> transicoes;
    int estadoInicial;
    ArrayList<Integer> estadosFinais;

    public AFD() {
        this.alfabeto = new ArrayList<>();
        this.estados = new ArrayList<>();
        this.transicoes = new ArrayList<>();
        this.estadoInicial = 0;
        this.estados.add(estadoInicial);
        this.estadosFinais = new ArrayList<>();
    }// 
    
    public AFD(ArrayList<Character> alfabeto, ArrayList<Integer> estados, 
            ArrayList transicoes, int estadoInicial, ArrayList<Integer> estadosFinais) {
        this.alfabeto = alfabeto;
        this.estados = estados;
        this.transicoes = transicoes;
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
    }// construtor

    @Override
    public void addSimbAlfabeto(Character novoSimb){
        if(!this.alfabeto.contains(novoSimb)){
            this.alfabeto.add(novoSimb);
        }// if
    }// addSimbAlfabeto
    
    public void addAlfabeto(ArrayList<Character> novoAlfabeto){
        this.alfabeto = novoAlfabeto;
    }
    
    @Override
    public void addEstado(int novoEstado, Boolean eFinal){
        if(!this.estados.contains(novoEstado)){
            this.estados.add(novoEstado);
        }// if
        
        if(eFinal && !this.estadosFinais.contains(novoEstado)){
            this.estadosFinais.add(novoEstado);
        }// if
    }// addEstado
    
    @Override
    public void addTransicao(int estados[], char simboloLido, int novosEstados[]){
        if(novosEstados.length != 1 || novosEstados.length != 1){
            System.out.println("""
                               Declaração de transição inválida!
                               AFD suporta apenas transições de um único estado para outro único estado,\n
                               não conjuntos de estados
                               """);
            return;
        }// if
        
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

    public ArrayList<Integer> getEstados() {
        return this.estados;
    }

    public int getEstadoInicial() {
        return this.estadoInicial;
    }

    public ArrayList<Transicao> getTransicoes() {
        return this.transicoes;
    }

    public ArrayList<Integer> getEstadosFinais() {
        return this.estadosFinais;
    }
    
}// class
