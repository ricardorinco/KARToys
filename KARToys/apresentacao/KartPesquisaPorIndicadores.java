package apresentacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;

import persistencia.KartP;
import util.Icons;

@SuppressWarnings("serial")
public class KartPesquisaPorIndicadores extends JDialog {
	
	private Icons icoFlag = new Icons();
	
	private KartTableModel modelo = new KartTableModel();
	private JTable tabela = new JTable(modelo);
	private JScrollPane tabelaScroll = new JScrollPane(tabela);
	
	private JLabel lblIndicadores = new JLabel("Indicadores:");
	private JLabel lblStatus = new JLabel();
	
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton rdbManutencao = new JRadioButton("Manutenção");
	private JRadioButton rdbEspecial = new JRadioButton("Especiais");
	private JRadioButton rdbAtivo = new JRadioButton("Ativos");
	private JRadioButton rdbTodos = new JRadioButton("Todos");
		
	public KartPesquisaPorIndicadores(JFrame pai) {
		super(pai, "Pesquisa de Kart's por Indicadores", ModalityType.MODELESS);

		desenhaTela();
		setIcon();
		setAtalhos();
		setTamanhosGrid();	
		limpaTela();		
		registraEventos();
		
		this.pack();
	}
	
	private void desenhaTela() {		
		group.add(rdbAtivo);
		group.add(rdbEspecial);
		group.add(rdbManutencao);
		group.add(rdbTodos);
		
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblIndicadores)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbAtivo)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbEspecial)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbManutencao)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbTodos)
						)
				)
				.addComponent(tabelaScroll)
				.addComponent(lblStatus, 375, 375, 375)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIndicadores)
						.addComponent(rdbAtivo)
						.addComponent(rdbEspecial)
						.addComponent(rdbManutencao)
						.addComponent(rdbTodos)
				)
				.addComponent(tabelaScroll)
				.addComponent(lblStatus)
		);
	}
	
	private void setIcon() {
		setIconImage(icoFlag.getIcoFlag());
	}
	
	private void setAtalhos() {
		rdbManutencao.setMnemonic('M');
		rdbEspecial.setMnemonic('E');
		rdbAtivo.setMnemonic('A');
		rdbTodos.setMnemonic('T');
	}
	
	private void setTamanhosGrid() {
		tabelaScroll.setPreferredSize(new Dimension(375, 300));
		tabela.getColumnModel().getColumn(0).setPreferredWidth(40); //Número da Placa
		tabela.getColumnModel().getColumn(1).setPreferredWidth(40); //Potência
		tabela.getColumnModel().getColumn(2).setPreferredWidth(20); //Especial
		tabela.getColumnModel().getColumn(3).setPreferredWidth(20); //Manutenção
		tabela.getColumnModel().getColumn(4).setPreferredWidth(20); //Ativo
		
		tabela.getColumnModel().getColumn(0).setResizable(false); //Número da Placa
		tabela.getColumnModel().getColumn(1).setResizable(false); //Potência
		tabela.getColumnModel().getColumn(2).setResizable(false); //Especial
		tabela.getColumnModel().getColumn(3).setResizable(false); //Manutenção
		tabela.getColumnModel().getColumn(4).setResizable(false); //Ativo
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
		
		rdbManutencao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rdb_Selected();
			}
		});
		
		rdbEspecial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rdb_Selected();
			}
		});
		
		rdbAtivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rdb_Selected();
			}
		});
		
		rdbTodos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rdb_Selected();
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
		
	private void rdb_Selected() {
		if (rdbManutencao.isSelected()) {
			populaTabelaKart(1);
		} else if (rdbEspecial.isSelected()) {
			populaTabelaKart(2);
		} else if (rdbAtivo.isSelected()) {
			populaTabelaKart(3);
		} else if (rdbTodos.isSelected()) {
			populaTabelaKart(0);
		}
		setTamanhosGrid();
	}
	
	private void escape_KeyPress() {
		this.dispose();
	}
	
	private void populaTabelaKart(int condicao) {
		try {
			KartP kartP = new KartP();
			modelo.setResultSet(kartP.PesquisaIndicadores(condicao));
			if (modelo.getRowCount() > 0) {
				lblStatus.setText("Registros encontrados: " + modelo.getRowCount());
			} else {
				JOptionPane.showMessageDialog(this, "Nenhum registro encontrado com o indicador solicitado.", "Informação", JOptionPane.OK_OPTION);
				limpaTela();
			}
		} catch (Exception e) {
			lblStatus.setText("ERRO: " + e.getMessage());
		}
	}
	
	private void limpaTela() {
		lblStatus.setForeground(new Color(129, 0, 0));
		lblStatus.setText("Selecione um indicador para efetuar a pesquisa");
		
		rdbManutencao.setSelected(false);
		rdbEspecial.setSelected(false);
		rdbAtivo.setSelected(false);
		rdbTodos.setSelected(false);
		
		if (!modelo.isEmpty()) {
			modelo.limpar();
		}	
	}
	
	private static void criaExibeGUI() {
		JDialog kartPesquisaPorIndicadores = new KartPesquisaPorIndicadores(null);
		kartPesquisaPorIndicadores.setVisible(true);
		kartPesquisaPorIndicadores.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		kartPesquisaPorIndicadores.setResizable(false);
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
