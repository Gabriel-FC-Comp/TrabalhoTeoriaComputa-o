
package interfaces;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import source.AFD;
import source.AFND;

public class Janela extends javax.swing.JFrame {

    /**
     * Creates new form Menu
     */
    private static Janela instancia = null;
    public AFND afnd;
    public AFD afd;
    
    public static Janela instanciaJanela(){
        if(Janela.instancia == null){
            Janela.instancia = new Janela();
            Janela.instancia.afnd = new AFND();
            Janela.instancia.afd = new AFD();
        } else{
            System.out.println("Instancia já aberta");
        }
        return Janela.instancia;
    }
    
    private Janela() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Conversor AFND/AFD");
        setMinimumSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 812, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 612, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[]) {
        Janela.instanciaJanela();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Janela.instancia.setVisible(true);
                Janela.instancia.setSize(600, 800);
            }
        });

        Janela.instancia.jPanel1 = new MenuGUI(Janela.instancia.afnd);
        Janela.instancia.jPanel1.setSize(899,699);
        Janela.instancia.jPanel1.setVisible(true);
        Janela.instancia.add(Janela.instancia.jPanel1);
        Janela.instancia.setSize(900, 700);
        
        Janela.instancia.jPanel2 = new ResultadosGUI(Janela.instancia.afd);
        Janela.instancia.jPanel2.setVisible(false);
        Janela.instancia.jPanel2.setSize(899,699);
    }
    
    public static Janela getInstancia(){
        return Janela.instancia;
    }
    
    public AFD getAfd() {
        return afd;
    }
    
    
    // Method 1
    static ArrayList<ArrayList<Integer> > subsets(int[] nums)
    {
  
        // Creating List class object
        // Declaring. object of integer List
        ArrayList<ArrayList<Integer> > output = new ArrayList<>();
  
        int n = nums.length;
  
        // Increase the size by using left shift (1 * 2^n)
        int size = 1 << n;
  
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> curr = new ArrayList<>();
            for (int j = 0; j < n; j++) {
  
                // right shift i and j i.e. i/2^j
                if (((i >> j) & 1) == 1) {
  
                    // Add it to subset
                    curr.add(nums[j]);
                }
            }
  
            // Adding the subset
            output.add(curr);
        }
        return output;
    }// Método adaptado de https://acervolima.com/programa-java-para-imprimir-todos-os-subconjuntos-exclusivos-em-uma-matriz-de-subconjuntos-usando-manipulacao-de-bits/
    
    public void converteAFN_AFD(){
        
        this.afd.addAlfabeto(this.afnd.getAlfabeto());
        
        int estados[] = this.afnd.getVetEstados();
        ArrayList<ArrayList<Integer>> possiveisEstados = subsets(estados);
        
        
        int qtdePossiveisEstados = possiveisEstados.size();
        for(int i = 1; i < qtdePossiveisEstados; i++){
            int sizeEp = possiveisEstados.get(i).size();
            Boolean eFinal = false;
            for(int e = 0; e < sizeEp; e++){
                if(this.afnd.getEstadosFinais().contains(possiveisEstados.get(i).get(e))){
                    eFinal = true;
                }
            }
            this.afd.addEstado((i-1), eFinal);
        }
                
        ArrayList<ArrayList<ArrayList<Integer>>> tabelaTransicoesPorEstado = this.afnd.tabelaTransicoesPorEstado();
        
        ArrayList<ArrayList<ArrayList<Integer>>> tabelaTransicoesPorEstadoPossivel = tabelaTransicoesPorEstadoPossivel(tabelaTransicoesPorEstado,possiveisEstados);

        addTransicoeTabela_AFD(tabelaTransicoesPorEstadoPossivel,possiveisEstados);
        
        Janela.instancia.jPanel2.updateUI();
    }
    
    public  ArrayList<ArrayList<ArrayList<Integer>>> tabelaTransicoesPorEstadoPossivel(ArrayList<ArrayList<ArrayList<Integer>>> tabela, 
            ArrayList<ArrayList<Integer>> possiveisEstados){
        
        ArrayList<ArrayList<ArrayList<Integer>>>  ret = new ArrayList<>();
        ArrayList<Integer> novosEstados;
        
        int qtdeEp = possiveisEstados.size();
        int qtdeE;
        int qtdeNe;
        int estadoIndex;
        int qtdeSimb = this.afnd.getAlfabeto().size();
        
        // Para cada simbolo do alfabeto
        for(int a = 0; a < qtdeSimb; a++){
            
            ret.add(a, new ArrayList<>());
            
            // Para cada conjunto de estados
            for(int ep = 1; ep < qtdeEp; ep++){
                
                novosEstados = new ArrayList<>();
                // Pega a qtde de estados que tem no conjunto
                qtdeE = possiveisEstados.get(ep).size();
                
                // Para cada subEstado
                for(int e = 0; e < qtdeE; e++){
                    
                    estadoIndex = this.afnd.getIndexEstado(possiveisEstados.get(ep).get(e));
                    qtdeNe = tabela.get(a).get(estadoIndex).size();
                    
                    // Para cada um dos novos estados
                    for(int ne = 0; ne < qtdeNe; ne++){
                        
                        if(!novosEstados.contains(tabela.get(a).get(estadoIndex).get(ne))){
                            novosEstados.add(tabela.get(a).get(estadoIndex).get(ne));   
                        }// if
                    }// for ne
                }// for e
                
                ret.get(a).add(novosEstados);
            }// for ep
        
        }// for a
        
        return ret;
    }
    
    public void addTransicoeTabela_AFD(ArrayList<ArrayList<ArrayList<Integer>>> tabela, ArrayList<ArrayList<Integer>> estadosPossiveis){
        
        int numSimb = this.afnd.getAlfabeto().size();
        int numEstados = this.afd.getEstados().size();
        int estado[] = new int[1];
        int novoEstado[] = new int[1];
        char simbLido;
        // Para cada simbolo
        
        for(int a = 0 ; a < numSimb; a++){
            // Para cada estado
            simbLido = this.afd.getAlfabeto().get(a);
            
            for(int e = 0; e < numEstados; e++){
                estado[0] = e;
                
                novoEstado[0] = -1;
                novoEstado[0] = estadosPossiveis.indexOf(tabela.get(a).get(e)) - 1;
                
                if(novoEstado[0] >= 0){                    
                    this.afd.addTransicao(estado, simbLido, novoEstado);
                }// if
            }// for e
        }// for a
    }
    
    public void buttonConverteMenuGUIAction(){
        
        Janela.instancia.jPanel1.setVisible(false);
        Janela.instancia.remove(Janela.instancia.jPanel1);
        
        this.converteAFN_AFD();
        
        Janela.instancia.jPanel2.setVisible(true);
        Janela.instancia.add(Janela.instancia.jPanel2);
        
    }
    
    public void buttonResetaResultadosGUIAction(){
        this.afnd.destroiAFND();
        buttonAlteraResultadosGUIAction();
    }
    
    public void buttonAlteraResultadosGUIAction(){
        
        Janela.instancia.jPanel2.setVisible(false);
        Janela.instancia.remove(Janela.instancia.jPanel2);
        Janela.instancia.jPanel1.setVisible(true);
        Janela.instancia.add(Janela.instancia.jPanel1);
        Janela.instancia.jPanel1.updateUI();
        
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }
    
    private JPanel jPanel1;
    private JPanel jPanel2;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
