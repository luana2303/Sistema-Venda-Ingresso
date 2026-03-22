package Venda_Ingresso.ui;

import Venda_Ingresso.services.GerenciadorIngresso;
import Venda_Ingresso.enums.Setor;
import Venda_Ingresso.entities.Ingresso;
import Venda_Ingresso.exceptions.QuantidadeInvalidaException;
import Venda_Ingresso.exceptions.SetorEsgotadoException;
import Venda_Ingresso.services.GerenciadorArquivo;
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

public class JanelaCadastroIngresso extends JDialog {

    private DefaultTableModel modelo;
    private JPanel painelFundo;
    private JButton btnSalvar;
    private JButton btnVoltarTelaInicial;
    private JLabel lblNome;
    private JLabel lblQtde;
    private JTextField txtNome;
    private JTextField txtQtde;

    private JComboBox<Setor> cbxSetores;

    private static final String PATH = "/home/cauan/Downloads/VendaIngressos/ingressos.ser";

    private String[] tiposTorcedor = {"Inteira", "Meia"};
    private JComboBox<String> cbxTipoTorcedor;

    private GerenciadorIngresso gerenciador;

    public JanelaCadastroIngresso(GerenciadorIngresso gerenciador) {
        this.gerenciador = gerenciador;
        criarComponentesInsercao();
    }

    private void limpar() {
        txtNome.setText("");
        txtQtde.setText("");
    }

    private void criarComponentesInsercao() {

        btnSalvar = new JButton("Salvar");
        btnVoltarTelaInicial = new JButton("Voltar para Tela Inicial");

        btnSalvar.addActionListener((e) -> comprarIngresso());

        btnVoltarTelaInicial.addActionListener((e) -> {
            GerenciadorArquivo.serializar(gerenciador.getIngressos(), PATH);
            setVisible(false);
            new TelaInicial(this, true, gerenciador);
        });

        lblNome = new JLabel("Nome:");
        lblQtde = new JLabel("Quantidade:");

        txtNome = new JTextField(10);
        txtQtde = new JTextField(5);

        cbxTipoTorcedor = new JComboBox<>(tiposTorcedor);
        cbxSetores = new JComboBox<>(Setor.values());

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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                GerenciadorArquivo.serializar(gerenciador.getIngressos(), PATH);
            }
        });
    }

    private void comprarIngresso() {

        Ingresso ingresso = new Ingresso();
        int quantidade;

        // 🔒 Validação segura
        try {
            String textoQtde = txtQtde.getText().trim();

            if (textoQtde.isEmpty()) {
                throw new QuantidadeInvalidaException("Informe a quantidade.");
            }

            quantidade = Integer.parseInt(textoQtde);

            if (quantidade <= 0) {
                throw new QuantidadeInvalidaException("Quantidade deve ser maior que zero.");
            }

        } catch (QuantidadeInvalidaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite apenas números inteiros.");
            return;
        }

        ingresso.setNome(txtNome.getText());
        Setor setorSelecionado = (Setor) cbxSetores.getSelectedItem();
        // Novo uso do enum
        try {

            setorSelecionado.comprar(quantidade);

        } catch (SetorEsgotadoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }
        double valorIngresso = setorSelecionado.getPreco();

        ingresso.setSetor(setorSelecionado.getNome());

        String tipoTorcedor = cbxTipoTorcedor.getSelectedItem().toString();

        // meia entrada
        if (tipoTorcedor.equalsIgnoreCase("Meia")) {
            valorIngresso /= 2;
        }

        ingresso.setQuantidade(quantidade);
        ingresso.setValor(valorIngresso);

        double valorTotal = valorIngresso * quantidade;
        ingresso.setValorTotal(valorTotal);

        ingresso.setDataHora(LocalDateTime.now());

        try {
            gerenciador.comprarIngresso(ingresso);

            limpar();
            JOptionPane.showMessageDialog(null, "Ingresso comprado com sucesso!");

        } catch (Exception e) {
            limpar();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
