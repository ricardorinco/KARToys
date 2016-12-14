package apresentacao;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import entidade.ClienteE;
import persistencia.ClienteP;
import util.DataHora;
import util.Icons;
import util.Text;

@SuppressWarnings("serial")
public class ClienteCadastro  extends JDialog {
	
	private Icons icoFlag = new Icons();
	private Text text = new Text();
	private DataHora data = new DataHora();
	
	private ClienteTableModel modelo = new ClienteTableModel();
	
	private JLabel lblNome = new JLabel("Nome:");
	private JLabel lblNumeroCPF =  new JLabel("CPF:");
	private JLabel lblSexo = new JLabel("Sexo:");
	private JLabel lblDataNascimento = new JLabel("Data Nascimento:");
	private JLabel lblEndereco =  new JLabel("Endereço:");
	private JLabel lblNumero = new JLabel("Número:");
	private JLabel lblBairro = new JLabel("Bairro:");
	private JLabel lblCidade = new JLabel("Cidade:");
	private JLabel lblUF = new JLabel("UF:");
	private JLabel lblCEP = new JLabel("CEP:");
	private JLabel lblTelefoneFixo =  new JLabel("Telefone Fixo:");
	private JLabel lblTelefoneCelular = new JLabel("Telefone Celular:");
	private JLabel lblDataCadastro =  new JLabel("Data Cadastro:");
	private JLabel lblStatus = new JLabel();
	
	private JTextField txtNome = new JTextField();
	private JTextField txtEndereco = new JTextField();
	private JTextField txtNumero = new JTextField();
	private JTextField txtBairro = new JTextField();
	private JTextField txtCidade = new JTextField();
	
	private JFormattedTextField txtNumeroCPF;
	private JFormattedTextField txtDataNascimento;
	private JFormattedTextField txtTelefoneFixo;
	private JFormattedTextField txtTelefoneMovel;
	private JFormattedTextField txtCEP;
	private JFormattedTextField txtDataCadastro;
	
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton rdbFeminino = new JRadioButton("Feminino");
	private JRadioButton rdbMasculino = new JRadioButton("Masculino");
	
	private JComboBox<String> cboUF = new JComboBox<String>();
			
	private JButton btnIncluir = new JButton("Incluir");
	private JButton btnAlterar = new JButton("Alterar");
			
	public ClienteCadastro(JFrame pai) {
		super(pai, "Cadastro de Cliente", ModalityType.MODELESS);
		
		setMascara();
		desenhaTela();
		setIcon();
		setAtalhos();
		preencheComboBoxUF();
		setTamanhoJTextField();	
		limpaTela();				
		registraEventos();
				
		this.pack();
	}
	
	private void desenhaTela() {
		group.add(rdbFeminino);
		group.add(rdbMasculino);
		
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblNumeroCPF)
							.addComponent(lblNome)
							.addComponent(lblDataNascimento)
							.addComponent(lblEndereco)
							.addComponent(lblBairro)
							.addComponent(lblUF)
							.addComponent(lblTelefoneFixo)
					)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addGroup(layout.createSequentialGroup()
									.addComponent(txtNumeroCPF, 100, 100, 100)
									.addComponent(lblDataCadastro, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(txtDataCadastro, 70, 70, 70)
							)
							.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(layout.createSequentialGroup()
									.addComponent(txtDataNascimento, 70, 70, 70)
									.addComponent(lblSexo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(rdbFeminino, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(rdbMasculino, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							)  
							.addGroup(layout.createSequentialGroup()
									.addComponent(txtEndereco, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblNumero)
									.addComponent(txtNumero, 39, 39, 39)
							)
							.addGroup(layout.createSequentialGroup()
									.addComponent(txtBairro, 120, 120, 120)
									.addComponent(lblCidade)
									.addComponent(txtCidade, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							)
							.addGroup(layout.createSequentialGroup()
									.addComponent(cboUF, 150, 150, 150)
									.addComponent(lblCEP, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(txtCEP, 75, 75, 75)
							)
							.addGroup(layout.createSequentialGroup()
									.addComponent(txtTelefoneFixo, 95, 95, 95)
									.addComponent(lblTelefoneCelular, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(txtTelefoneMovel, 100, 100, 100)
							)
							.addGroup(layout.createSequentialGroup()
									.addComponent(btnIncluir, 80, 80, 80)
									.addComponent(btnAlterar, 80, 80, 80)
							)
					)
				)
				.addComponent(lblStatus, 40, 450, 450)
		);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumeroCPF)
						.addComponent(txtNumeroCPF)
						.addComponent(lblDataCadastro)
						.addComponent(txtDataCadastro)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNome)
						.addComponent(txtNome)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDataNascimento)
						.addComponent(txtDataNascimento)
						.addComponent(lblSexo)
						.addComponent(rdbFeminino)
						.addComponent(rdbMasculino)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEndereco)
						.addComponent(txtEndereco)
						.addComponent(lblNumero)
						.addComponent(txtNumero)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCidade)
						.addComponent(txtCidade)
						.addComponent(lblBairro)
						.addComponent(txtBairro)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUF)
						.addComponent(cboUF)
						.addComponent(lblCEP)
						.addComponent(txtCEP)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTelefoneFixo)
						.addComponent(txtTelefoneFixo)
						.addComponent(lblTelefoneCelular)
						.addComponent(txtTelefoneMovel)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnIncluir, 20, 20, 20)
						.addComponent(btnAlterar, 20, 20 ,20)
				)
				.addComponent(lblStatus)
		);
	}
	
	private void setIcon() {
		setIconImage(icoFlag.getIcoFlag());
	}
	
	private void setAtalhos() {
		rdbFeminino.setMnemonic('F');
		rdbMasculino.setMnemonic('M');
		btnIncluir.setMnemonic('I');
		btnAlterar.setMnemonic('A');
	}
	
	private void setTamanhoJTextField() {
		txtNome.setDocument(new Text(50));
		txtEndereco.setDocument(new Text(40));
		txtNumero.setDocument(new Text(7));
		txtBairro.setDocument(new Text(30));
		txtCidade.setDocument(new Text(30));
	}
	
	private void setMascara() {
		try {
			txtNumeroCPF =  new JFormattedTextField(text.setMask("###.###.###-##"));
			txtDataNascimento = new JFormattedTextField(text.setMask("##/##/####"));
			txtTelefoneFixo =  new JFormattedTextField(text.setMask("(##) ####-####"));
			txtTelefoneMovel = new JFormattedTextField(text.setMask("(##) #####-####"));
			txtCEP = new JFormattedTextField(text.setMask("##.###-###"));
			txtDataCadastro = new JFormattedTextField(text.setMask("##/##/####"));
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
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
		
		txtNumeroCPF.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtNumeroCPF_FocusLost();
			}
		});
		
		btnIncluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnIncluir_Click();
			}			
		});
		
		btnAlterar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnAlterar_Click();	
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
		
	private void txtNumeroCPF_FocusLost() {
		try {
			if (text.replace(txtNumeroCPF.getText()).trim().isEmpty() || text.replace(txtNumeroCPF.getText()).trim().length() < 11 ) {
				lblStatus.setText("ATENÇÃO: Informe o número do CPF.");
				txtNumeroCPF.requestFocusInWindow();
			} else {
				lblStatus.setText(" ");
				ClienteP clienteP = new ClienteP();
				modelo.setResultSet(clienteP.PesquisaPorCPF(text.replace(txtNumeroCPF.getText())));
				
				if (modelo.getRowCount() == 1) {
					popularDadosClienteParaTela();
					txtNumeroCPF.setEnabled(false);
					btnAlterar.setEnabled(true);
				} else {
					txtDataCadastro.setText(data.getDataAtual());
					btnIncluir.setEnabled(true);
				}
			}
		} catch (NumberFormatException | SQLException e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}

	private void btnIncluir_Click() {
		try {
			ClienteP clienteP = new ClienteP();
			ClienteE clienteE = popularDadosTelaCliente();
			clienteP.Incluir(clienteE);
			JOptionPane.showMessageDialog(this, "Cliente incluído com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
			limpaTela();
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void btnAlterar_Click() {
		try {
			ClienteP clienteP = new ClienteP();
			ClienteE clienteE = popularDadosTelaCliente();
			clienteP.Alterar(clienteE);
			JOptionPane.showMessageDialog(this, "Cliente alterado com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
			limpaTela();
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void escape_KeyPress() {
		if (text.replace(txtNumeroCPF.getText()).trim().isEmpty()) {
			this.dispose();
		} else {
			limpaTela();
		}
	}
	
	private void popularDadosClienteParaTela() {
		ClienteE cliente = modelo.getValueAt(0);
		
		txtNome.setText(cliente.nome);
		if (cliente.sexo.equals("F")){
			rdbFeminino.setSelected(true);
		} else {
			rdbMasculino.setSelected(true);
		}
		
		txtDataNascimento.setText(text.formataData(cliente.dataNascimento));
		txtEndereco.setText(cliente.endereco);
		txtNumero.setText(cliente.numero);
		txtBairro.setText(cliente.bairro);
		txtCidade.setText(cliente.cidade);
		cboUF.setSelectedItem(cliente.uf);
		txtCEP.setText(cliente.cep);
		txtTelefoneFixo.setText(cliente.telefoneFixo);
		txtTelefoneMovel.setText(cliente.telefoneCelular);
		txtDataCadastro.setText(text.formataData(cliente.dataCadastro));
	}
	
	private ClienteE popularDadosTelaCliente() throws ParseException {
		ClienteE cliente = new ClienteE();
		
		cliente.numeroCPF = text.replace(txtNumeroCPF.getText());
		cliente.nome = txtNome.getText();
		cliente.sexo = rdbFeminino.isSelected() ? "F" : "M";
		cliente.endereco = txtEndereco.getText();
		cliente.numero = txtNumero.getText();
		cliente.bairro = txtBairro.getText();
		cliente.cidade = txtCidade.getText();
		cliente.uf = String.valueOf(cboUF.getSelectedItem());
		cliente.cep = text.replace(txtCEP.getText());
		cliente.telefoneFixo = txtTelefoneFixo.getText();
		cliente.telefoneCelular = txtTelefoneMovel.getText();
		cliente.dataNascimento = text.formataData(txtDataNascimento.getText());
		cliente.dataCadastro = text.formataData(txtDataCadastro.getText());
		
		return cliente;
	} 
	
	private void preencheComboBoxUF() {
		cboUF.addItem("Selecione o Estado");
		cboUF.addItem("AC");
		cboUF.addItem("AL");
		cboUF.addItem("AP");
		cboUF.addItem("AM");
		cboUF.addItem("BA");
		cboUF.addItem("CE");
		cboUF.addItem("DF");
		cboUF.addItem("ES");
		cboUF.addItem("GO");
		cboUF.addItem("MA");
		cboUF.addItem("MT");
		cboUF.addItem("MS");
		cboUF.addItem("MG");
		cboUF.addItem("PA");
		cboUF.addItem("PB");
		cboUF.addItem("PR");
		cboUF.addItem("PE");
		cboUF.addItem("PI");
		cboUF.addItem("RJ");
		cboUF.addItem("RN");
		cboUF.addItem("RS");
		cboUF.addItem("RO");
		cboUF.addItem("RR");
		cboUF.addItem("SC");
		cboUF.addItem("SP");
		cboUF.addItem("SE");
		cboUF.addItem("TO");
	}
				
	private void limpaTela() {
		lblStatus.setForeground(new Color(129, 0, 0));
		lblStatus.setText("");
		
		txtNumeroCPF.setValue(null);
		txtNome.setText("");
		txtEndereco.setText("");
		txtBairro.setText("");
		txtCidade.setText("");
		txtNumero.setText("");
		txtCEP.setValue(null);
		txtDataNascimento.setValue(null);
		txtTelefoneFixo.setValue(null);
		txtTelefoneMovel.setValue(null);
		txtDataCadastro.setValue(null);
		txtDataCadastro.setEnabled(false);
		
		rdbFeminino.setSelected(true);
		cboUF.setSelectedIndex(0);
		
		btnIncluir.setEnabled(false);
		btnAlterar.setEnabled(false);

		txtNumeroCPF.setEnabled(true);
		txtNumeroCPF.requestFocusInWindow();
	}
	
	private static void criaExibeGUI() {
		JDialog clienteCadastro = new ClienteCadastro(null);
		clienteCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		clienteCadastro.setVisible(true);
		clienteCadastro.setResizable(false);
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
	
	