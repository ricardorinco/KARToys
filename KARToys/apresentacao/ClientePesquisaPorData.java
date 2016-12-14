package apresentacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import persistencia.ClienteP;
import util.Icons;
import util.Text;

@SuppressWarnings("serial")
public class ClientePesquisaPorData extends JDialog {
	
	private Icons icoFlag = new Icons();
	private Text text = new Text();
	
	private ClienteTableModel modelo = new ClienteTableModel();
	private JTable tabela = new JTable(modelo);
	private JScrollPane tabelaScroll = new JScrollPane(tabela);
	
	private JLabel lblData = new JLabel("Data de:");
	private JLabel lblDataAte = new JLabel("até");
	private JLabel lblStatus = new JLabel();
	
	private JFormattedTextField txtDataInicial;
	private JFormattedTextField txtDataFinal;
	
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton rdbDataNascimento = new JRadioButton("Data de Nascimento");
	private JRadioButton rdbDataCadastro = new JRadioButton("Data de Cadastro");
	
	private JButton btnPesquisar = new JButton("Pesquisar");
	
	public ClientePesquisaPorData(JFrame pai) {
		super(pai, "Pesquisa de Clientes por Data", ModalityType.MODELESS);
		
		setMascara();
		desenhaTela();
		setIcon();
		setAtalhos();
		setTamanhoGrid();
		limpaTela();
		registraEventos();	
		
		this.pack();
	}
	
	private void desenhaTela() {		
		group.add(rdbDataNascimento);
		group.add(rdbDataCadastro);
		
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblData)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtDataInicial, 70, 70, 70)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDataAte)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtDataFinal, 70, 70, 70)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbDataNascimento)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbDataCadastro)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnPesquisar)
						)
				)
				.addComponent(tabelaScroll)
				.addComponent(lblStatus, 1100, 1100, 1100)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblData)
						.addComponent(txtDataInicial)
						.addComponent(lblDataAte)
						.addComponent(txtDataFinal)
						.addComponent(rdbDataNascimento)
						.addComponent(rdbDataCadastro)
						.addComponent(btnPesquisar, 20, 20, 20)
				)
				.addComponent(tabelaScroll)
				.addComponent(lblStatus)
		);
	}
	
	private void setIcon() {
		setIconImage(icoFlag.getIcoFlag());
	}
	
	private void setAtalhos() {
		rdbDataNascimento.setMnemonic('N');
		rdbDataCadastro.setMnemonic('C');
		btnPesquisar.setMnemonic('P');
	}
	
	private void setMascara() {
		try {
			txtDataInicial = new JFormattedTextField(text.setMask("##/##/####"));
			txtDataFinal = new JFormattedTextField(text.setMask("##/##/####"));
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void setTamanhoGrid() {
		tabelaScroll.setPreferredSize(new Dimension(1100, 300));
		tabela.getColumnModel().getColumn(0).setPreferredWidth(60);  //Número CPF
		tabela.getColumnModel().getColumn(1).setPreferredWidth(140); //Nome
		tabela.getColumnModel().getColumn(2).setPreferredWidth(5);   //Sexo
		tabela.getColumnModel().getColumn(3).setPreferredWidth(50);  //Data Nascimento
		tabela.getColumnModel().getColumn(4).setPreferredWidth(140); //Endreço
		tabela.getColumnModel().getColumn(5).setPreferredWidth(30);  //Número
		tabela.getColumnModel().getColumn(6).setPreferredWidth(50);  //Bairro
		tabela.getColumnModel().getColumn(7).setPreferredWidth(50);  //Cidade
		tabela.getColumnModel().getColumn(8).setPreferredWidth(1);   //UF
		tabela.getColumnModel().getColumn(9).setPreferredWidth(50);  //CEP
		tabela.getColumnModel().getColumn(10).setPreferredWidth(70); //Telefone Fixo
		tabela.getColumnModel().getColumn(11).setPreferredWidth(75); //Telefone Celular
		tabela.getColumnModel().getColumn(12).setPreferredWidth(50); //Data Cadastro
		
		tabela.getColumnModel().getColumn(0).setResizable(false);  //Número CPF
		tabela.getColumnModel().getColumn(1).setResizable(false);  //Nome
		tabela.getColumnModel().getColumn(2).setResizable(false);  //Sexo
		tabela.getColumnModel().getColumn(3).setResizable(false);  //Data Nascimento
		tabela.getColumnModel().getColumn(4).setResizable(false);  //Endreço
		tabela.getColumnModel().getColumn(5).setResizable(false);  //Número
		tabela.getColumnModel().getColumn(6).setResizable(false);  //Bairro
		tabela.getColumnModel().getColumn(7).setResizable(false);  //Cidade
		tabela.getColumnModel().getColumn(8).setResizable(false);  //UF
		tabela.getColumnModel().getColumn(9).setResizable(false);  //UF
		tabela.getColumnModel().getColumn(10).setResizable(false); //Telefone Fixo
		tabela.getColumnModel().getColumn(11).setResizable(false); //Telefone Celular
		tabela.getColumnModel().getColumn(12).setResizable(false); //Data Cadastro
	}
	
	private void registraEventos() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				limpaTela();
				dispose();
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				limpaTela();
				dispose();
			}
		});
		
		txtDataInicial.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtDataInicial_FocusLost();
			}
		});
		
		txtDataFinal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtDataFinal_FocusLost();
			}
		});
		
		txtDataFinal.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				txtDatas_TextChanged();
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				txtDatas_TextChanged();
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				txtDatas_TextChanged();
			}
		});
		
		btnPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnPesquisar_Click(); 
			}
		});
		
		Action escapeAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				escape_KeyPress();
			}
		};
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
		getRootPane().getActionMap().put("ESCAPE", escapeAction);
		
	}
	
	private void txtDataInicial_FocusLost() {
		if (text.replace(txtDataInicial.getText()).trim().isEmpty() || text.replace(txtDataInicial.getText()).trim().length() < 8) {
			lblStatus.setText("ATENÇÃO: Informe a data inicial da pesquisa.");
			txtDataInicial.requestFocusInWindow();
		} else {
			lblStatus.setText(" ");
		}
	}
	
	private void txtDataFinal_FocusLost() {
		if (!text.replace(txtDataInicial.getText()).trim().isEmpty()) {
			if (text.replace(txtDataFinal.getText()).trim().isEmpty() || text.replace(txtDataFinal.getText()).trim().length() < 8 ) {
				lblStatus.setText("ATENÇÃO: Informe a data final da pesquisa.");
				txtDataFinal.requestFocusInWindow();
			} else {
				lblStatus.setText(" ");
			}
		}
	}
	
	private void txtDatas_TextChanged() {
		if (text.replace(txtDataFinal.getText()).isEmpty() || text.replace(txtDataFinal.getText()).length() < 8) {
			btnPesquisar.setEnabled(false);
		} else {
			btnPesquisar.setEnabled(true);
		}
	}
	
	private void btnPesquisar_Click() {
		populaTabelaCliente();
		setTamanhoGrid();
	}
	
	private void escape_KeyPress() {
		if (text.replace(txtDataInicial.getText()).trim().isEmpty() && text.replace(txtDataFinal.getText()).trim().isEmpty()) {
			this.dispose();
		} else {
			limpaTela();
		}
	}
	
	private void populaTabelaCliente() {
		try {
			ClienteP clienteP = new ClienteP();				
			modelo.setResultSet(clienteP.PesquisaCompletaPorDataNascimento(text.formataData(txtDataInicial.getText()),
					text.formataData(txtDataFinal.getText()),
					rdbDataNascimento.isSelected() ? true : false));
			if (modelo.getRowCount() > 0) {
				lblStatus.setText("Registros encontrados: " + modelo.getRowCount());
			} else {
				JOptionPane.showMessageDialog(this, "Nenhum registro encontrado com a cidade pesquisada.", "Informação", JOptionPane.OK_OPTION);
				limpaTela();
			}			
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
				
	private void limpaTela() {
		lblStatus.setForeground(new Color(129, 0, 0));
		lblStatus.setText(" ");
		
		txtDataInicial.setValue(null);
		txtDataFinal.setValue(null);		
		
		if (!modelo.isEmpty()) {
			modelo.limpar();
		}	
		
		rdbDataNascimento.setSelected(true);
		
		btnPesquisar.setEnabled(false);
		
		txtDataInicial.requestFocusInWindow();	
	}
	
	private static void criaExibeGUI() {
		JDialog clientePesquisaPorDataNascimento = new ClientePesquisaPorData(null);
		clientePesquisaPorDataNascimento.setVisible(true);
		clientePesquisaPorDataNascimento.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		clientePesquisaPorDataNascimento.setResizable(false);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				criaExibeGUI();
			}
		});
	}
	
}
