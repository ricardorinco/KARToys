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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import persistencia.KartP;
import util.Icons;
import util.Text;
import entidade.KartE;

@SuppressWarnings("serial")
public class KartCadastro extends JDialog {	
	
	private Icons icoFlag = new Icons();
	
	private KartTableModel modelo = new KartTableModel();
	
	private JLabel lblStatus = new JLabel();
	
	private JLabel lblNumeroKart = new JLabel("Número do Kart: ");
	private JLabel lblCategoria = new JLabel("Categoria: ");
	private JLabel lblEspecial = new JLabel("Especial: ");
	private JLabel lblManutencao = new JLabel("Manutenção: ");
	private JLabel lblAtivo = new JLabel("Ativo: ");
	
	private JTextField txtNumeroKart = new JTextField();
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton rdbAmador = new JRadioButton("Amador");
	private JRadioButton rdbProfissional = new JRadioButton("Profissional");
	
	private JCheckBox chkEspecial = new JCheckBox();
	private JCheckBox chkManutencao = new JCheckBox();
	private JCheckBox chkAtivo = new JCheckBox();
	
	private JButton btnIncluir = new JButton("Incluir");
	private JButton btnAlterar = new JButton("Alterar");
			
	public KartCadastro(JFrame pai) {
		super(pai, "Cadastro de Kart", ModalityType.MODELESS);
		
		desenhaTela();
		setAtalhos();
		setTamanhoJTextField();	
		limpaTela();
		registraEventos();
		
		this.pack();
	}
	
	private void desenhaTela() {
		setIconImage(icoFlag.getIcoFlag());
		
		group.add(rdbAmador);
		group.add(rdbProfissional);
		
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblNumeroKart)
							.addComponent(lblCategoria)
							.addComponent(lblEspecial)
							.addComponent(lblManutencao)
							.addComponent(lblAtivo)
					)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtNumeroKart, 60, 60, Short.MAX_VALUE)
							.addGroup(layout.createSequentialGroup()
									.addComponent(rdbAmador)
									.addComponent(rdbProfissional)
							)
							.addComponent(chkEspecial)
							.addComponent(chkManutencao)
							.addComponent(chkAtivo)
							.addGroup(layout.createSequentialGroup()
									.addComponent(btnIncluir)
									.addComponent(btnAlterar)
							)
					)
				)
				.addComponent(lblStatus, 280, 280, 280)
		);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumeroKart)
						.addComponent(txtNumeroKart)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCategoria)
						.addComponent(rdbAmador)
						.addComponent(rdbProfissional)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEspecial)
						.addComponent(chkEspecial)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblManutencao)
						.addComponent(chkManutencao)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAtivo)
						.addComponent(chkAtivo)
				)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnIncluir, 20, 20, 20)
						.addComponent(btnAlterar, 20, 20, 20)
				)
				.addComponent(lblStatus)
		);
	}
	
	private void setAtalhos() {
		rdbAmador.setMnemonic('m');
		rdbProfissional.setMnemonic('r');
		btnIncluir.setMnemonic('I');
		btnAlterar.setMnemonic('A');
	}
	
	private void setTamanhoJTextField() {
		txtNumeroKart.setDocument(new Text(7));
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
		
		txtNumeroKart.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtNumeroKart_FocusLost();
			}
		});
		
		chkManutencao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chkManutencao_Selected();
			}
		});
		
		chkAtivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chkAtivo_Selected();
			}
		});
		
		btnIncluir.addActionListener(new ActionListener() {
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

	private void txtNumeroKart_FocusLost() {
		try {
			if (txtNumeroKart.getText().trim().isEmpty()) {
				lblStatus.setText("ATENÇÃO: Informe o número do kart.");
				txtNumeroKart.requestFocusInWindow();
			} else {
				lblStatus.setText(" ");
				KartP kartP = new KartP();
				modelo.setResultSet(kartP.PesquisaPorNumeroKart(Integer.parseInt(txtNumeroKart.getText())));
				
				if (modelo.getRowCount() == 1) {
					popularDadosKartParaTela();
					txtNumeroKart.setEnabled(false);
					btnAlterar.setEnabled(true);
				} else {
					btnIncluir.setEnabled(true);
				}
			}
		} catch (NumberFormatException | SQLException e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void chkManutencao_Selected() {
		if (chkManutencao.isSelected() && chkAtivo.isSelected()) {
			chkAtivo.setSelected(false);
		}
	}
	
	private void chkAtivo_Selected() {
		if (chkAtivo.isSelected() && chkManutencao.isSelected()) {
			chkManutencao.setSelected(false);
		}
	}
	
	private void btnIncluir_Click() {
		try {
			KartP kartP = new KartP();
			KartE kartE = popularDadosTelaParaKart();			
			kartP.Incluir(kartE);			
			JOptionPane.showMessageDialog(this, "Kart incluído com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
			limpaTela();
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void btnAlterar_Click() {
		try {
			KartP kartP = new KartP();
			KartE kartE = popularDadosTelaParaKart();			
			kartP.Alterar(kartE);			
			JOptionPane.showMessageDialog(this, "Kart alterado com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
			limpaTela();
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void escape_KeyPress() {
		if (txtNumeroKart.getText().trim().equals("")) {
			this.dispose();
		} else {
			limpaTela();
		}
	}
	
	private void popularDadosKartParaTela() {	
		KartE kart = modelo.getValueAt(0);
		
		if (kart.categoria.equals("A")) {
			rdbAmador.setSelected(true);
		} else {
			rdbProfissional.setSelected(true);
		}
		chkEspecial.setSelected(kart.especial);
		chkManutencao.setSelected(kart.manutencao);
		chkAtivo.setSelected(kart.ativo);
	}
	
	private KartE popularDadosTelaParaKart() {
		KartE kart = new KartE();
		
		kart.numerokart = Integer.parseInt(txtNumeroKart.getText());		
		kart.categoria = rdbAmador.isSelected() ? "A" : "B";
		if (kart.especial = chkEspecial.isSelected());
		kart.ativo = chkAtivo.isSelected();
		kart.manutencao = chkManutencao.isSelected();
		
		return kart;
	}
	
	private void limpaTela() {
		lblStatus.setForeground(new Color(129, 0, 0));
		lblStatus.setText("");
		
		txtNumeroKart.setText("");
		rdbAmador.setSelected(true);
		chkEspecial.setSelected(false);
		chkManutencao.setSelected(false);
		chkAtivo.setSelected(true);
		
		btnIncluir.setEnabled(false);
		btnAlterar.setEnabled(false);
					
		txtNumeroKart.setEnabled(true);
		txtNumeroKart.requestFocusInWindow();
	}
	
	private static void criaExibeGUI() {
		JDialog kartCadastro = new KartCadastro(null);
		kartCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		kartCadastro.setVisible(true);
		kartCadastro.setResizable(false);
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
