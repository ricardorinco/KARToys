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
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import persistencia.ClienteP;
import util.Icons;
import util.Text;

@SuppressWarnings("serial")
public class ClientePesquisaPorNome extends JDialog {
	
	private Icons icoFlag = new Icons();
	
	private ClienteTableModel modelo = new ClienteTableModel();
	private JTable tabela = new JTable(modelo);
	private JScrollPane tabelaScroll = new JScrollPane(tabela);
	
	private JLabel lblCidade = new JLabel("Nome do Cliente:");
	private JLabel lblStatus = new JLabel();
	
	private JTextField txtNome = new JTextField();
	
	private JButton btnPesquisar = new JButton("Pesquisar");
	
	public ClientePesquisaPorNome(JFrame pai) {
		super(pai, "Pesquisa de Clientes por Nome", ModalityType.MODELESS);
		
		desenhaTela();
		setIcon();
		setAtalhos();
		setTamanhoGrid();
		setTamanhoJTextField();
		limpaTela();
		registraEventos();	
		
		this.pack();
	}
	
	private void desenhaTela() {	
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCidade)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtNome, 300, 300, 300)
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
						.addComponent(lblCidade)
						.addComponent(txtNome)
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
		btnPesquisar.setMnemonic('P');
	}
	
	private void setTamanhoJTextField() {
		txtNome.setDocument(new Text(30));
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
		
		txtNome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtNome_FocusLost();
			}
		});
		
		txtNome.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				txtNome_TextChanged();
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				txtNome_TextChanged();
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				txtNome_TextChanged();
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
	
	private void txtNome_FocusLost() {
		if (txtNome.getText().trim().isEmpty()) {
			lblStatus.setText("ATENÇÃO: Informe o nome do cliente a ser pesquisado.");
			txtNome.requestFocusInWindow();
		} else {
			lblStatus.setText(" ");
		}
	}
	
	private void txtNome_TextChanged() {
		if (txtNome.getText().isEmpty()) {
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
		if (txtNome.getText().trim().isEmpty()) {
			this.dispose();
		} else {
			limpaTela();
		}
	}
	
	private void populaTabelaCliente() {
		try {
			ClienteP clienteP = new ClienteP();
			modelo.setResultSet(clienteP.PesquisaCompletaPorNome(txtNome.getText()));
			if (modelo.getRowCount() > 0) {
				lblStatus.setText("Registros encontrados: " + modelo.getRowCount());
			} else {
				setTamanhoGrid();
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
		
		txtNome.setText("");
		
		if (!modelo.isEmpty()) {
			modelo.limpar();
		}
		
		btnPesquisar.setEnabled(false);
		
		txtNome.requestFocusInWindow();		
	}
	
	private static void criaExibeGUI() {
		JDialog clientePesquisaPorCidade = new ClientePesquisaPorNome(null);
		clientePesquisaPorCidade.setVisible(true);
		clientePesquisaPorCidade.setResizable(false);
		clientePesquisaPorCidade.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
