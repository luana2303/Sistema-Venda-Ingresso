/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venda_Ingresso;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author Junior
 */
public class TelaInicial extends JDialog {   
    
    private JPanel painelFundo;
    private JButton btnComprar;
    private JButton btnGerarRelatorio;
    
    ArrayList<Ingresso> ingressos;
     
    GerenciadorIngresso gerenciador = new GerenciadorIngresso();
   
    
    public TelaInicial() {    
        criarComponentesTela();  
    }
    
    public TelaInicial(JanelaCadastroIngresso cadastro, boolean isModal, ArrayList<Ingresso> ingressos) {
        super(cadastro, isModal);       
        this.ingressos = ingressos;
        criarComponentesTela();
    } 
    
    private void criarComponentesTela() {
        btnComprar = new JButton("Comprar Ingresso");
        btnGerarRelatorio = new JButton("Gerar Relatório");
        
        btnComprar.addActionListener((e) -> {           
           JanelaCadastroIngresso janelaCadastroIngresso = new JanelaCadastroIngresso();
           janelaCadastroIngresso.setVisible(true); 
        });       
        
        btnGerarRelatorio.addActionListener((e) -> {           
           setVisible(false);
           JanelaGrafica janelaGrafica = new JanelaGrafica();
           
           janelaGrafica.setVisible(true); 
           janelaGrafica.imprimirRelatorio(ingressos);
        });
        
        painelFundo = new JPanel();
        painelFundo.add(btnComprar);
        painelFundo.add(btnGerarRelatorio);
        
        add(painelFundo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);// Só fecha a janela(Esconde). Não fecha a aplicação(EXIT_ON_CLOSE)
        setLocationRelativeTo(null);
        pack();
        setSize(300, 200);
        setVisible(true);
     }
}
