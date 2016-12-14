package apresentacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
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

import entidade.SecaoE;
import persistencia.SecaoP;
import util.DataHora;
import util.Icons;
import util.Text;

@SuppressWarnings("serial")
public class SecaoCadastro extends JDialog {
	
	private Icons icoFlag = new Icons();
	private Text text = new Text();
	private DataHora data = new DataHora();
	
	private SecaoTableModel modelo = new SecaoTableModel();
	private JTable tabela = new JTable(modelo);
	private JScrollPane tabelaScroll = new JScrollPane(tabela);
		
	private JLabel lblBateria = new JLabel("Bateria: ");
	private JLabel lblDataBateria = new JLabel("Data: ");
	private JLabel lblHorario = new JLabel("Horário: ");
	private JLabel lblCategoria = new JLabel("Categoria: ");
	private JLabel lblStatus = new JLabel();
	
	private JLabel lblIdBateria = new JLabel();
	private JFormattedTextField txtHorario; 
	private JFormattedTextField txtDataBateria;
	
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton rdbAmador = new JRadioButton("Amador");
	private JRadioButton rdbProfissional = new JRadioButton("Profissional");
	
	private JButton btnIncluir = new JButton("Incluir");
	
	public SecaoCadastro(JFrame pai) {
		super(pai, "Cadastro de Seções", ModalityType.MODELESS);

		setMascara();
		desenharTela();
		setTamanhosGrid();
		setIcon();
		setAtalhos();
		limpaTela();
		registraEventos();
		popularTabelaSecao();
				
		this.pack();
	}
		
	private void desenharTela() {
		group.add(rdbAmador);
		group.add(rdbProfissional);
				
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBateria)
							.addComponent(lblDataBateria)
							.addComponent(lblCategoria)
					)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblIdBateria)
							.addGroup(layout.createSequentialGroup()
									.addComponent(txtDataBateria, 70, 70, 70)
									.addComponent(lblHorario, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(txtHorario, 40, 40, 40)
							)
							.addGroup(layout.createSequentialGroup()
									.addComponent(rdbAmador, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(rdbProfissional, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnIncluir)
							)
					)
				)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(tabelaScroll)
						)
				)
				.addComponent(lblStatus, 350, 350, 350)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(lblBateria)
						.addComponent(lblIdBateria)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(lblDataBateria, 20, 20, 20)
						.addComponent(txtDataBateria, 20, 20, 20)
						.addComponent(lblHorario, 20, 20, 20)
						.addComponent(txtHorario, 20, 20, 20)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(lblCategoria, 20, 20 ,20)
						.addComponent(rdbAmador, 20, 20, 20)
						.addComponent(rdbProfissional)
						.addComponent(btnIncluir, 20, 20, 20)
				)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(tabelaScroll)
				)
				.addComponent(lblStatus)
		);
	}
	
	private void setIcon() {
		setIconImage(icoFlag.getIcoFlag());
	}
	
	private void setAtalhos() {
		rdbAmador.setMnemonic('m');
		rdbProfissional.setMnemonic('r');
		btnIncluir.setMnemonic('I');
	}

	private void setMascara() {
		try {
			txtHorario =  new JFormattedTextField(text.setMask("##:##"));
			txtDataBateria = new JFormattedTextField(text.setMask("##/##/####"));
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void setTamanhosGrid() {
		tabelaScroll.setPreferredSize(new Dimension(250, 300));
		tabela.getColumnModel().getColumn(0).setPreferredWidth(60); //Número da Placa
		tabela.getColumnModel().getColumn(1).setPreferredWidth(60); //Potência
		tabela.getColumnModel().getColumn(2).setPreferredWidth(60); //Especial
		tabela.getColumnModel().getColumn(3).setPreferredWidth(60); //Categoria
		
		tabela.getColumnModel().getColumn(0).setResizable(false); //Número da Placa
		tabela.getColumnModel().getColumn(1).setResizable(false); //Potência
		tabela.getColumnModel().getColumn(2).setResizable(false); //Especial
		tabela.getColumnModel().getColumn(3).setResizable(false); //Categoria
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
		
		txtDataBateria.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtDataBateria_FocusLost();
			}
		});
		
		txtHorario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtHorario_FocusLost();
			}
		});
		
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				tabela_Click(evt);
			}
		});
		
		btnIncluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnIncluir_Click();
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
	
	private void txtDataBateria_FocusLost() {
		if (text.replace(txtDataBateria.getText()).trim().isEmpty() || text.replace(txtDataBateria.getText()).trim().length() < 8 ) {
			lblStatus.setText("ATENÇÃO: Informe a data da seção.");
			txtDataBateria.requestFocusInWindow();
		} else {
			verificaStatusSecao();
			lblStatus.setText(" ");
		}
	}
	
	private void txtHorario_FocusLost() {
		if (!text.replace(txtDataBateria.getText()).trim().isEmpty()) {
			try {
				if (text.replace(txtHorario.getText()).trim().isEmpty() || text.replace(txtHorario.getText()).trim().length() < 4 ) {
					lblStatus.setText("ATENÇÃO: Informe o horário da seção.");
					txtHorario.requestFocusInWindow();
				} else {
					lblStatus.setText(" ");
					SecaoP secaoP = new SecaoP();
					modelo.setResultSet(secaoP.PesquisaPorBateria(text.formataData(txtDataBateria.getText()),
							                                      txtHorario.getText()));
					
					if (modelo.getRowCount() == 1) {
						popularDadosSecaoParaTela(0);
						txtDataBateria.setEnabled(false);
						txtHorario.setEnabled(false);
						JOptionPane.showMessageDialog(this, "Já existe uma seção criada com os dados informados.\nSeção número: " + lblIdBateria.getText(), "Informação", JOptionPane.INFORMATION_MESSAGE);
					} else {
						btnIncluir.setEnabled(true);
						rdbAmador.requestFocusInWindow();
						popularTabelaSecao();
					}
				}
			} catch (NumberFormatException | SQLException | ParseException e) {
				lblStatus.setText("ERRO: " + e.getMessage());
			}
		}
	}
	
	private void tabela_Click(MouseEvent evt) {
		int linha = tabela.rowAtPoint(evt.getPoint());
		if (linha >= 0) {
			popularDadosSecaoParaTela(linha);
			protegeTela();
		}
	}
	
	private void btnIncluir_Click() {
		try {
			SecaoP secaoP = new SecaoP();
			SecaoE secaoE = popularDadosTelaSecao();
			
			int idBateria = secaoP.Incluir(secaoE);
			lblIdBateria.setText(String.valueOf(idBateria));
			
			JOptionPane.showMessageDialog(this, "Bateria incluída na Seção com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
			limpaTela();
			popularTabelaSecao();
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void popularDadosSecaoParaTela(int linha) {
		SecaoE secao = modelo.getValueAt(linha);
		
		lblIdBateria.setText(String.valueOf(secao.bateria));
		txtDataBateria.setText(String.valueOf(text.formataData(secao.dataBateria)));
		txtHorario.setText(String.valueOf(secao.horario));
		if (secao.categoria.equals("A")) {
			rdbAmador.setSelected(true);
		} else {
			rdbProfissional.setSelected(true);
		}
	}
	
	private SecaoE popularDadosTelaSecao() throws ParseException {
		SecaoE secao = new SecaoE();
		
		if (lblIdBateria.getText().equals("")) {
			secao.bateria = null;
		} else {
			secao.bateria = Integer.parseInt(lblIdBateria.getText());
		}
		
		secao.dataBateria = text.formataData(txtDataBateria.getText());
		secao.horario = txtHorario.getText();
		secao.categoria = rdbAmador.isSelected() ? "A" : "N";
		
		return secao;
	}
	
	private void popularTabelaSecao() {
		try {
			setTamanhosGrid();
			SecaoP secaoP = new SecaoP();
			modelo.setResultSet(secaoP.PesquisaSecoes());
			lblStatus.setText("Registros encontrados: " + modelo.getRowCount());
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void escape_KeyPress() {
		if (text.replace(txtDataBateria.getText()).trim().isEmpty()) {
			this.dispose();
		} else {
			limpaTela();
		}
	}
	
	private void verificaStatusSecao() {
		try {
			if (!data.comparaData(txtDataBateria.getText())) {
				JOptionPane.showMessageDialog(this, "Não é possível cadastrar uma nova Seção com a data retroativa.\nData informada: " + txtDataBateria.getText(), "Informação", JOptionPane.INFORMATION_MESSAGE);
				limpaTela();
			}
		} catch (HeadlessException | ParseException e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void protegeTela() {
		txtDataBateria.setEnabled(false);
		txtHorario.setEnabled(false);
		
		rdbAmador.setEnabled(false);
		rdbProfissional.setEnabled(false);
	}
	
	private void limpaTela() {
		lblStatus.setForeground(new Color(129, 0, 0));
		lblStatus.setText(" ");
		
		lblBateria.setFont(new Font("Arial", Font.BOLD, 20));
		lblIdBateria.setFont(new Font("Arial", Font.BOLD, 20));
		lblIdBateria.setForeground(Color.ORANGE);
		
		txtDataBateria.setEnabled(true);
		txtHorario.setEnabled(true);
		
		rdbAmador.setSelected(true);
		rdbAmador.setEnabled(true);
		rdbProfissional.setEnabled(true);
		
		lblIdBateria.setText("");
		txtDataBateria.setValue(null);
		txtHorario.setValue(null);
		
		txtDataBateria.requestFocusInWindow();
		
		popularTabelaSecao();
		
		btnIncluir.setEnabled(false);
	}
	
	private static void criaExibeGUI() {
		  JDialog secaoCadastro = new SecaoCadastro(null);
		  secaoCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		  secaoCadastro.setVisible(true);
		  secaoCadastro.setResizable(false);
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
