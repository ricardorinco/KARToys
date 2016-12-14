package apresentacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import entidade.ClienteE;
import persistencia.ClienteP;
import util.Icons;
import util.Text;

@SuppressWarnings("serial")
public class ClientePickList extends JDialog {
	
	private String numeroCPF;
	private boolean clickTabela = false;
	
	private Icons icoFlag = new Icons();
	
	private ClientePickListTableModel modelo = new ClientePickListTableModel();
	private JTable tabela = new JTable(modelo);
	private JScrollPane tabelaScroll = new JScrollPane(tabela);
	
	private JLabel lblNome = new JLabel("Nome do Cliente:");
	private JLabel lblStatus = new JLabel();
	
	private JTextField txtNome = new JTextField();
	
	private JButton btnPesquisar = new JButton("Pesquisar");
	
	public ClientePickList(JFrame pai) {
		super(pai, "PickList - Pesquisa de Clientes", ModalityType.MODELESS);
		
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
								.addComponent(lblNome)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnPesquisar)
						)
				)
				.addComponent(tabelaScroll)
				.addComponent(lblStatus, 350, 350, 350)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNome)
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
		tabelaScroll.setPreferredSize(new Dimension(200, 300));
		tabela.getColumnModel().getColumn(0).setPreferredWidth(20);  //Número CPF
		tabela.getColumnModel().getColumn(1).setPreferredWidth(190); //Nome
		
		tabela.getColumnModel().getColumn(0).setResizable(false);  //Número CPF
		tabela.getColumnModel().getColumn(1).setResizable(false);  //Nome
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
		
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				tabela_Click(evt);
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
	
	private void tabela_Click(MouseEvent evt) {
		int linha = tabela.rowAtPoint(evt.getPoint());
		if (linha >= 0) {
			popularDadosClienteParaTela(linha);
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
	
	private void popularDadosClienteParaTela(int linha) {
		ClienteE cliente = modelo.getValueAt(linha);
		
		numeroCPF = cliente.numeroCPF;
		clickTabela = true;
		this.dispose();
	}
	
	private void populaTabelaCliente() {
		try {
			ClienteP clienteP = new ClienteP();
			modelo.setResultSet(clienteP.PesquisaPickList(txtNome.getText()));
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

    public boolean alterarDados() {
    	clickTabela = false;
    	
        setModal(true);
        setVisible(true);
        
        return clickTabela; 
    }
	
    public String getNumeroCPF() {
        return numeroCPF;
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
		JDialog clientePickList = new ClientePickList(null);
		clientePickList.setVisible(true);
		clientePickList.setResizable(false);
		clientePickList.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
