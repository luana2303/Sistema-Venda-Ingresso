/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venda_Ingresso;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Junior
 */
public class JanelaCadastroIngresso extends JDialog {
    
    private DefaultTableModel modelo;
    private JPanel painelFundo;
    private JButton btnSalvar;
    private JButton btnVoltarTelaInicial;
    private JLabel lblCodigo;
    private JLabel lblNome;
    private JLabel lblValor;
    private JLabel lblQtde;
    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtSetor;
    private JTextField txtQtde;
    
    private String[] setores
            = {"Amarelo","Azul","Branco","Verde"};

    private JComboBox<String> cbxSetores;
    
    private String[] tiposTorcedor
            = {"Inteira", "Meia"};

    private JComboBox<String> cbxTipoTorcedor;

    GerenciadorIngresso gerenciador = new GerenciadorIngresso();
    
    String setor = "";
    String tipoTorcedor = "";
    
        
    public JanelaCadastroIngresso() {
        criarComponentesInsercao();        
    }

    private void limpar(){
        txtNome.setText("");
        txtQtde.setText("");        
    }

    private void criarComponentesInsercao() {
        
        
        btnSalvar = new JButton("Salvar");        
        btnVoltarTelaInicial  = new JButton("Voltar para Tela Inicial");
        
        btnSalvar.addActionListener((e) -> {
           comprarIngresso();
        });        
       
        btnVoltarTelaInicial.addActionListener((e) -> {
            setVisible(false);
            new TelaInicial(this, true, gerenciador.getIngressos());
        });
        
        lblNome = new JLabel("Nome:");       
        cbxTipoTorcedor = new JComboBox(tiposTorcedor);
        lblQtde = new JLabel("Quantidade:");
        txtNome = new JTextField(10);        
        txtQtde = new JTextField(5);
        cbxSetores = new JComboBox(setores);

        painelFundo = new JPanel();
        painelFundo.add(lblNome);
        painelFundo.add(txtNome);   
        painelFundo.add(cbxTipoTorcedor);
        painelFundo.add(lblQtde);
        painelFundo.add(txtQtde);
        painelFundo.add(cbxSetores);
        painelFundo.add(btnSalvar);
        painelFundo.add(btnVoltarTelaInicial);

        add(painelFundo);        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);// Só fecha a janela(Esconde). Não fecha a aplicação(EXIT_ON_CLOSE)
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
    
    private void comprarIngresso() {        
        Ingresso ingresso = new Ingresso();
        double valorIngresso = 0.00;
        
        ingresso.setNome(txtNome.getText());          
        
        setor = cbxSetores.getSelectedItem().toString();
        
        // Em caso de alteracao, novo item eh adicionado ao atributo setor
        cbxSetores.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){   
                    setor = cbxSetores.getSelectedItem().toString();                                     
                }
            }
        });        
        
        ingresso.setSetor(setor);   
        ingresso.setQuantidade(Integer.parseInt(txtQtde.getText())); 
        
        // Identifica valores dos ingressos
        if (setor.equalsIgnoreCase("Amarelo")){
            valorIngresso = 180.00;            
        }else{
            if (setor.equalsIgnoreCase("Azul")){
                valorIngresso = 100.00;
            }else{
                if (setor.equalsIgnoreCase("Branco")){
                    valorIngresso = 60.00;
                }else{
                    if (setor.equalsIgnoreCase("Verde")){
                        valorIngresso = 350.00;
                    }
                }
            }
        }
        
        tipoTorcedor = cbxTipoTorcedor.getSelectedItem().toString();
        
        // Em caso de alteracao, novo item eh adicionado ao atributo setor
        cbxSetores.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){   
                    tipoTorcedor = cbxTipoTorcedor.getSelectedItem().toString();                                     
                }
            }
        });     
        
        // se for estudante ou aposentado, calcula meia entrada
        if (tipoTorcedor.equalsIgnoreCase("Meia")){
            valorIngresso = valorIngresso/2;
        }
        
        ingresso.setValor(valorIngresso);        
        
        // calcula o valor total
        double valorTotal = ingresso.getValor() * ingresso.getQuantidade();
        ingresso.setValorTotal(valorTotal);
        
        // captura a data e hora local da maquina
        ingresso.setDataHora(LocalDateTime.now());            
        
        if (gerenciador.comprarIngresso(ingresso)) {            
            limpar();
            JOptionPane.showMessageDialog(null, "Ingresso comprado com sucesso!");
        } else {
            limpar();
            JOptionPane.showMessageDialog(null, "Ingressos esgotados! Por favor, selecione outro setor.");
        }  
        
    }               
}
