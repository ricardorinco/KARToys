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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import entidade.DetalheSecaoE;
import entidade.SecaoE;
import persistencia.ClienteP;
import persistencia.DetalheSecaoP;
import persistencia.KartP;
import persistencia.SecaoP;
import util.DataHora;
import util.Icons;
import util.Text;

@SuppressWarnings("serial")
public class DetalheSecaoCadastro extends JDialog {
	
	private boolean secaoEncerrada = false;
	
	private Icons icoFlag = new Icons();
	private Text text = new Text();
	private DataHora data = new DataHora();
	
	private SecaoTableModel modeloSecao = new SecaoTableModel();
	private JTable tabelaSecao = new JTable(modeloSecao);
	private JScrollPane tabelaScrollSecao = new JScrollPane(tabelaSecao);
	
	private DetalheSecaoTableModel modeloDetalheSecao = new DetalheSecaoTableModel();
	private JTable tabelaDetalheSecao = new JTable(modeloDetalheSecao);
	private JScrollPane tabelaScrollDetalheSecao = new JScrollPane(tabelaDetalheSecao);
		
	private JLabel lblBateria = new JLabel("Bateria: ");
	private JLabel lblDataBateria = new JLabel("Data: ");
	private JLabel lblHorario = new JLabel("Horário: ");
	private JLabel lblNumeroCPF = new JLabel("CPF: ");
	private JLabel lblPlaca = new JLabel("Kart: ");
	private JLabel lblStatus = new JLabel();
	private JLabel lblStatusSecao = new JLabel();
	private JLabel lblStatusDetalheSecao = new JLabel();
	
	private JLabel lblIdBateria = new JLabel();
	private JTextField txtNumeroKart = new JTextField();
	
	private JFormattedTextField txtHorario;
	private JFormattedTextField txtNumeroCPF;
	private JFormattedTextField txtDataBateria;
	
	private JButton btnPesquisar = new JButton("Pesquisar");
	private JButton btnIncluir = new JButton("Incluir");
	private JButton btnExcluir = new JButton("Excluir");
	
	public DetalheSecaoCadastro(JFrame pai) {
		super(pai, "Bateria - Detalhamento de Seção", ModalityType.MODELESS);
		
		setMascara();
		desenharTela();
		setIcon();
		setAtalhos();
		limpaTela();
		registraEventos();
		popularTabelaSecao();
		setTamanhosGridSecao();
		setTamanhosGridDetalheSecao();
		
		this.pack();
	}
	
	private void desenharTela() {
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBateria)
							.addComponent(lblDataBateria)
					)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblIdBateria)
							.addGroup(layout.createSequentialGroup()
									.addComponent(txtDataBateria, 70, 70, 70)
									.addComponent(lblHorario)
									.addComponent(txtHorario, 40, 40, 40)
									.addComponent(lblNumeroCPF)
									.addComponent(txtNumeroCPF, 100, 100, 100)
									.addComponent(lblPlaca)
									.addComponent(txtNumeroKart, 60, 60, 60)
									.addComponent(btnPesquisar)
									.addComponent(btnIncluir)
									.addComponent(btnExcluir)
							)
					)
				)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(lblStatusSecao, 277, 277, 277)
								.addComponent(lblStatusDetalheSecao)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(tabelaScrollSecao)
								.addComponent(tabelaScrollDetalheSecao)
						)
				)
				.addComponent(lblStatus, 900, 900, 900)
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
						.addComponent(lblNumeroCPF, 20, 20, 20)
						.addComponent(txtNumeroCPF, 20, 20, 20)
						.addComponent(lblPlaca, 20, 20, 20)
						.addComponent(txtNumeroKart, 20, 20, 20)
						.addComponent(btnPesquisar, 20, 20, 20)
						.addComponent(btnIncluir, 20, 20, 20)
						.addComponent(btnExcluir, 20, 20, 20)
				)
				.addGroup(layout.createParallelGroup()
						.addComponent(lblStatusSecao)
						.addComponent(lblStatusDetalheSecao)
				)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(tabelaScrollSecao)
						.addComponent(tabelaScrollDetalheSecao)
				)
				.addComponent(lblStatus)
		);
	}
	
	private void setIcon() {
		setIconImage(icoFlag.getIcoFlag());
	}
	
	private void setAtalhos() {
		btnPesquisar.setMnemonic('P');
		btnIncluir.setMnemonic('I');
		btnExcluir.setMnemonic('E');
	}
	
	private void setMascara() {
		try {
			txtHorario =  new JFormattedTextField(text.setMask("##:##"));
			txtDataBateria = new JFormattedTextField(text.setMask("##/##/####"));
			txtNumeroCPF = new JFormattedTextField(text.setMask("###.###.###-##"));
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void setTamanhosGridSecao() {
		tabelaScrollSecao.setPreferredSize(new Dimension(270, 300));
		tabelaSecao.getColumnModel().getColumn(0).setPreferredWidth(5); //Seção
		tabelaSecao.getColumnModel().getColumn(1).setPreferredWidth(40); //Data
		tabelaSecao.getColumnModel().getColumn(2).setPreferredWidth(5);  //Hora
		tabelaSecao.getColumnModel().getColumn(3).setPreferredWidth(5);  //Categoria
		
		tabelaSecao.getColumnModel().getColumn(0).setResizable(false); //Seção
		tabelaSecao.getColumnModel().getColumn(1).setResizable(false); //Data
		tabelaSecao.getColumnModel().getColumn(2).setResizable(false); //Hora
		tabelaSecao.getColumnModel().getColumn(3).setResizable(false); //Categoria
	}
	
	private void setTamanhosGridDetalheSecao() {
		tabelaScrollDetalheSecao.setPreferredSize(new Dimension(600, 300));
		tabelaDetalheSecao.getColumnModel().getColumn(0).setPreferredWidth(20);  //Bateria
		tabelaDetalheSecao.getColumnModel().getColumn(1).setPreferredWidth(90);  //CPF
		tabelaDetalheSecao.getColumnModel().getColumn(2).setPreferredWidth(300); //Nome
		tabelaDetalheSecao.getColumnModel().getColumn(3).setPreferredWidth(10);  //Kart
		tabelaDetalheSecao.getColumnModel().getColumn(4).setPreferredWidth(10);  //Potencia
		tabelaDetalheSecao.getColumnModel().getColumn(5).setPreferredWidth(10);  //Especial
		
		tabelaDetalheSecao.getColumnModel().getColumn(0).setResizable(false);  //Bateria
		tabelaDetalheSecao.getColumnModel().getColumn(1).setResizable(false);  //CPF
		tabelaDetalheSecao.getColumnModel().getColumn(2).setResizable(false);  //Nome
		tabelaDetalheSecao.getColumnModel().getColumn(3).setResizable(false);  //Kart
		tabelaDetalheSecao.getColumnModel().getColumn(4).setResizable(false);  //Potencia
		tabelaDetalheSecao.getColumnModel().getColumn(5).setResizable(false);  //Especial
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
				
		txtNumeroCPF.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnPesquisar.setEnabled(true);
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				txtNumeroCPF_FocusLost();
			}
		});
		
		txtNumeroKart.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnPesquisar.setEnabled(true);
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				txtNumeroKart_FocusLost();
			}
		});
		
		tabelaDetalheSecao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				tabelaDetalheSecao_Click(evt);
			}
		});
		
		tabelaSecao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				tabelaSecao_Click(evt);
			}
		});
		
		btnPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPesquisar_Click();
			}
		});
		
		btnIncluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnIncluir_Click();
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnExcluir_Click();
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
		if (text.replace(txtDataBateria.getText()).trim().isEmpty() || text.replace(txtDataBateria.getText()).trim().length() < 6 ) {
			lblStatus.setText("ATENÇÃO: Informe a data da bateria.");
			txtDataBateria.requestFocusInWindow();
		} else {
			lblStatus.setText(" ");
		}
	}
		
	private void txtHorario_FocusLost() {
		if (!text.replace(txtDataBateria.getText()).trim().isEmpty()) {
			protegeTela();
			try {
				if (text.replace(txtHorario.getText()).trim().isEmpty() || text.replace(txtHorario.getText()).trim().length() < 4 ) {
					lblStatus.setText("ATENÇÃO: Informe o horário da seção.");
					txtHorario.setEnabled(true);
					txtHorario.requestFocusInWindow();
				} else {
					lblStatus.setText(" ");
					SecaoP secaoP = new SecaoP();
					modeloSecao.setResultSet(secaoP.PesquisaPorBateria(text.formataData(txtDataBateria.getText()), txtHorario.getText()));
					
					if (modeloSecao.getRowCount() == 1) {
						popularDadosSecaoParaTela(0);
						txtDataBateria.setEnabled(false);
						txtHorario.setEnabled(false);
						verificaStatusSecao();
						popularTabelaDetalheSecao();
					} else {
						JOptionPane.showMessageDialog(this, "A Seção pesquisada não está disponível.", "Informação", JOptionPane.INFORMATION_MESSAGE);
						limpaTela();
					}
					
					if (secaoEncerrada) {
						lblStatus.setText(" ");
					}
				}
				setTamanhosGridDetalheSecao();
			} catch (NumberFormatException | SQLException | ParseException e) {
				lblStatus.setText("ERRO: " + e.getMessage());
			}
		}
	}
	
	private void txtNumeroCPF_FocusLost() {
		if (!text.replace(txtHorario.getText()).trim().isEmpty()) {
			try {
				if (text.replace(txtNumeroCPF.getText()).trim().isEmpty() || text.replace(txtNumeroCPF.getText()).trim().length() < 11 ) {
					lblStatus.setText("ATENÇÃO: Informe o número do CPF.");
					txtNumeroCPF.requestFocusInWindow();
				} else {
					lblStatus.setText(" ");
					ClienteP clienteP = new ClienteP();
					
					if (!clienteP.PesquisaCPF(text.replace(txtNumeroCPF.getText()))) {
						lblStatus.setText(" ");
						txtNumeroKart.setEnabled(false);
						JOptionPane.showMessageDialog(this, "Número de CPF não cadastrado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
						txtNumeroCPF.setText("");
						txtNumeroCPF.requestFocusInWindow();
						txtNumeroKart.setEnabled(true);
					}
				}
				if (!text.replace(txtNumeroCPF.getText()).trim().isEmpty()) {
					btnPesquisar.setEnabled(false);
				}
				if (secaoEncerrada) {
					lblStatus.setText(" ");
				}
			} catch (NumberFormatException | SQLException e) {
				lblStatus.setText("ERRO: " + e.getMessage());
			}
		}
	}
	
	private void txtNumeroKart_FocusLost() {
		if (!text.replace(txtNumeroCPF.getText()).trim().isEmpty()) {
			try {
				if (txtNumeroKart.getText().trim().isEmpty()) {
					lblStatus.setText("ATENÇÃO: Informe a placa do kart.");
					txtNumeroKart.requestFocusInWindow();
				} else {
					lblStatus.setText(" ");
					KartP kartP = new KartP();
					
					if (!kartP.PesquisaNumeroKart(Integer.parseInt(txtNumeroKart.getText()))) {
						JOptionPane.showMessageDialog(this, "Kart não Disponível para esta corrida", "Informação", JOptionPane.INFORMATION_MESSAGE);
						txtNumeroKart.setText("");
						txtNumeroKart.requestFocusInWindow();
					} else {
						btnIncluir.setEnabled(true);
						btnIncluir.requestFocusInWindow();
					}
				}
				if (!txtNumeroKart.getText().trim().isEmpty()) {
					btnPesquisar.setEnabled(false);
				}
			} catch (NumberFormatException | SQLException e) {
				lblStatus.setText("ERRO: " + e.getMessage());
			}
		}
	}
	
	private void tabelaSecao_Click(MouseEvent evt) {
		int linha = tabelaSecao.rowAtPoint(evt.getPoint());
		if (linha >= 0) {
			popularDadosSecaoParaTela(linha);
			verificaStatusSecao();
			popularTabelaDetalheSecao();
			setTamanhosGridSecao();
			setTamanhosGridDetalheSecao();
			btnPesquisar.setEnabled(false);
			if (secaoEncerrada) {
				lblStatus.setText(" ");
			}
		}
	}
	
	private void tabelaDetalheSecao_Click(MouseEvent evt) {
		int linha = tabelaDetalheSecao.rowAtPoint(evt.getPoint());
		if (linha >= 0) {
			popularDadosDetalheSecaoParaTela(linha);
		}
	}
	
	private void btnPesquisar_Click() {
		if (text.replace(txtNumeroCPF.getText()).trim().isEmpty()) {
			ClientePickList pickListCliente = new ClientePickList(null);
			pickListCliente.setResizable(false);
			pickListCliente.setLocationRelativeTo(this);
			if (pickListCliente.alterarDados()) {
				txtNumeroCPF.setText(pickListCliente.getNumeroCPF());
			}
			pickListCliente.dispose();
        } else if (!text.replace(txtNumeroCPF.getText()).trim().isEmpty() && txtNumeroKart.getText().trim().isEmpty()) {
        	KartPickList pickListKart = new KartPickList(null);
        	pickListKart.setResizable(false);
        	pickListKart.setLocationRelativeTo(this);
			if (pickListKart.alterarDados()) {
				txtNumeroKart.setText(pickListKart.getNumeroKart());
			}
			pickListKart.dispose();
        }

    }
	
	private void btnIncluir_Click() {
		try {
			DetalheSecaoP detalheSecaoP = new DetalheSecaoP();
			DetalheSecaoE detalheSecaoE = popularDadosTelaSecao();
			
			detalheSecaoP.Incluir(detalheSecaoE);			
			JOptionPane.showMessageDialog(this, "Cliente incluído com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
			limpaTela();
		} catch (Exception e) {
			lblStatus.setText("ERRO:" + e.getMessage());
		}
	}
	
	private void btnExcluir_Click() {
		try {
			DetalheSecaoP detalheSecaoP = new DetalheSecaoP();
			DetalheSecaoE detalheSecaoE = popularDadosTelaSecao();
			int dialogResultado = JOptionPane.showConfirmDialog(this,
				"Deseja realmente excluir o cliente selecionado desta Seção?",
				"Confirmação", JOptionPane.YES_NO_OPTION);
			detalheSecaoP.Excluir(detalheSecaoE);
			if (dialogResultado == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
				popularTabelaDetalheSecao();
			} else {
				popularDadosTelaSecao();
				JOptionPane.showMessageDialog(this, "Operação cancelada!", "Informação", JOptionPane.INFORMATION_MESSAGE);
			}
			limpaTela();
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void escape_KeyPress() {
		if (text.replace(txtDataBateria.getText()).trim().isEmpty() && text.replace(txtHorario.getText()).trim().isEmpty() ) {
			this.dispose();
		} else {
			limpaTela();
		}
	}
	
	private void popularTabelaSecao() {
		try {
			SecaoP secaoP = new SecaoP();
			modeloSecao.setResultSet(secaoP.PesquisaSecoes());
			setTamanhosGridSecao();
			if (modeloSecao.getRowCount() > 0) {
				lblStatusSecao.setText("Seções Encontradas:" + modeloSecao.getRowCount());
			} else {
				lblStatusSecao.setText(" ");
			}
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private DetalheSecaoE popularDadosTelaSecao() throws ParseException {
		DetalheSecaoE detalheSecao = new DetalheSecaoE();
		
		detalheSecao.bateria = Integer.parseInt(lblIdBateria.getText());
		detalheSecao.numeroCPF = text.replace(txtNumeroCPF.getText());
		detalheSecao.numeroKart = Integer.parseInt(txtNumeroKart.getText());
		
		return detalheSecao;
	}

	private void popularTabelaDetalheSecao() {
		try {
			DetalheSecaoP detalheSecaoP = new DetalheSecaoP();
			modeloDetalheSecao.setResultSet(detalheSecaoP.PesquisaDetalhes(Integer.parseInt(lblIdBateria.getText())));
			
			if (modeloDetalheSecao.getRowCount() == 10) {
				setTamanhosGridDetalheSecao();
				if (!secaoEncerrada) {
					JOptionPane.showMessageDialog(this, "Seção atingiu a capacidade máxima.");
				}
				protegeTela();
			}
			if (modeloDetalheSecao.getRowCount() > 0) {
				lblStatusDetalheSecao.setText("Pilotos nesta seção: " + modeloDetalheSecao.getRowCount());
			} else {
				lblStatusDetalheSecao.setText(" ");
			}
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void popularDadosSecaoParaTela(int linha) {
		SecaoE secao = modeloSecao.getValueAt(linha);
		
		lblIdBateria.setText(String.valueOf(secao.bateria));
		txtDataBateria.setText(String.valueOf(text.formataData(secao.dataBateria)));
		txtHorario.setText(secao.horario);
		
		txtDataBateria.setEnabled(false);
		txtHorario.setEnabled(false);
		txtNumeroCPF.requestFocusInWindow();
		
		txtNumeroCPF.setValue(null);
		txtNumeroKart.setText("");
	}	
	
	private void popularDadosDetalheSecaoParaTela(int linha) {
		DetalheSecaoE detalheSecao = modeloDetalheSecao.getValueAt(linha);
		
		txtNumeroCPF.setText(String.valueOf(detalheSecao.numeroCPF));
		txtNumeroKart.setText(String.valueOf(detalheSecao.numeroKart));
		
		btnPesquisar.setEnabled(false);
		btnIncluir.setEnabled(false);
		if (!secaoEncerrada) {
			btnExcluir.setEnabled(true);
		}
		
		txtNumeroCPF.setEnabled(false);
		txtNumeroKart.setEnabled(false);
	}
						
	private void verificaStatusSecao() {
		try {
			btnPesquisar.setEnabled(false);
			if (!data.comparaData(txtDataBateria.getText())) {
				protegeTela();
				JOptionPane.showMessageDialog(this, "Seção encerrada.", "Informação", JOptionPane.INFORMATION_MESSAGE);
				btnPesquisar.setEnabled(false);
				secaoEncerrada = true;
			} 
			
			if (txtDataBateria.getText().equals(data.getDataAtual())) {
				if (!data.comparaHoras(txtHorario.getText().trim())) {
					protegeTela();
					JOptionPane.showMessageDialog(this, "Seção encerrada.", "Informação", JOptionPane.INFORMATION_MESSAGE);
					secaoEncerrada = true;
				} else {
					txtNumeroCPF.setEnabled(true);
					txtNumeroKart.setEnabled(true);
					secaoEncerrada = false;
				}
			} else {
				txtNumeroCPF.setEnabled(true);
				txtNumeroKart.setEnabled(true);
				secaoEncerrada = false;
			}
			
		} catch (HeadlessException | ParseException e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}

	private void protegeTela() {
		txtDataBateria.setEnabled(false);
		txtHorario.setEnabled(false);
		txtNumeroCPF.setEnabled(false);
		txtNumeroKart.setEnabled(false);
		
		lblStatus.setText(" ");
		
		btnPesquisar.setEnabled(false);
		btnIncluir.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
	
	private void limpaTela() {
		lblStatus.setForeground(new Color(129, 0, 0));
		lblStatusSecao.setForeground(new Color(255, 165, 0));
		lblStatusDetalheSecao.setForeground(new Color(255, 165, 0));
		lblStatus.setText(" ");
		lblStatusSecao.setText(" ");
		lblStatusDetalheSecao.setText(" ");
		
		lblBateria.setFont(new Font("Arial", Font.BOLD, 20));
		lblIdBateria.setFont(new Font("Arial", Font.BOLD, 20));
		lblIdBateria.setForeground(new Color(255, 165, 0));
		
		lblIdBateria.setText("");
		txtDataBateria.setValue(null);
		txtHorario.setValue(null);
		txtNumeroCPF.setValue(null);
		txtNumeroKart.setText("");
		
		txtDataBateria.setEnabled(true);
		txtHorario.setEnabled(true);
		txtNumeroCPF.setEnabled(true);
		txtNumeroKart.setEnabled(true);
		
		txtDataBateria.requestFocusInWindow();
		
		if (!modeloDetalheSecao.isEmpty()) {
			modeloDetalheSecao.limpar();
		}	
		
		popularTabelaSecao();
		
		btnPesquisar.setEnabled(false);
		btnIncluir.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
		
	private static void criaExibeGUI() {
		  JDialog detalheSecaoCadastro = new DetalheSecaoCadastro(null);
		  detalheSecaoCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		  detalheSecaoCadastro.setVisible(true);
		  detalheSecaoCadastro.setResizable(false);
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
