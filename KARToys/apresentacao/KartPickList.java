package apresentacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import entidade.KartE;
import persistencia.KartP;
import util.Icons;

@SuppressWarnings("serial")
public class KartPickList extends JDialog {
	
	private Integer numeroKart ;
	private boolean clickTabela = false;
	
	private Icons icoFlag = new Icons();
	
	private KartPickListTableModel modelo = new KartPickListTableModel();
	private JTable tabela = new JTable(modelo);
	private JScrollPane tabelaScroll = new JScrollPane(tabela);
	
	private JLabel lblIndicadores = new JLabel("Indicadores:");
	private JLabel lblStatus = new JLabel();
	
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton rdbEspecial = new JRadioButton("Especiais");
	private JRadioButton rdbTodos = new JRadioButton("Todos");
		
	public KartPickList(JFrame pai) {
		super(pai, "PickList - Pesquisa de Kart's", ModalityType.MODELESS);

		desenhaTela();
		setIcon();
		setAtalhos();
		setTamanhosGrid();	
		limpaTela();		
		registraEventos();
		
		this.pack();
	}
	
	private void desenhaTela() {		
		group.add(rdbEspecial);
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
								.addComponent(rdbEspecial)
						)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbTodos)
						)
				)
				.addComponent(tabelaScroll)
				.addComponent(lblStatus, 200, 200, 200)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIndicadores)
						.addComponent(rdbEspecial)
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
		rdbEspecial.setMnemonic('E');
		rdbTodos.setMnemonic('T');
	}
	
	private void setTamanhosGrid() {
		tabelaScroll.setPreferredSize(new Dimension(200, 300));
		tabela.getColumnModel().getColumn(0).setPreferredWidth(40); //Número da Placa
		tabela.getColumnModel().getColumn(1).setPreferredWidth(40); //Potência
		tabela.getColumnModel().getColumn(2).setPreferredWidth(20); //Especial
		
		tabela.getColumnModel().getColumn(0).setResizable(false); //Número da Placa
		tabela.getColumnModel().getColumn(1).setResizable(false); //Potência
		tabela.getColumnModel().getColumn(2).setResizable(false); //Especial
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
		
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				tabelaSecao_Click(evt);
			}
		});
		
		rdbEspecial.addActionListener(new ActionListener() {
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
		if (rdbEspecial.isSelected()) {
			populaTabelaKart(true);
		} else if (rdbTodos.isSelected()) {
			populaTabelaKart(false);
		}
		setTamanhosGrid();
	}
	
	private void tabelaSecao_Click(MouseEvent evt) {
		int linha = tabela.rowAtPoint(evt.getPoint());
		if (linha >= 0) {
			popularDadosSecaoParaTela(linha);
		}
	}
	
	private void escape_KeyPress() {
		this.dispose();
	}
	
	private void populaTabelaKart(boolean condicao) {
		try {
			KartP kartP = new KartP();
			modelo.setResultSet(kartP.PesquisaPickList(condicao));
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
	
	private void popularDadosSecaoParaTela(int linha) {
		KartE kart = modelo.getValueAt(linha);
		
		numeroKart = kart.numerokart;
		clickTabela = true;
		this.dispose();
	}
	
	private void limpaTela() {
		lblStatus.setForeground(new Color(129, 0, 0));
		lblStatus.setText("Selecione um indicador para efetuar a pesquisa");
		
		rdbEspecial.setSelected(false);
		rdbTodos.setSelected(false);
		
		if (!modelo.isEmpty()) {
			modelo.limpar();
		}	
	}
	
	private static void criaExibeGUI() {
		JDialog kartPesquisaPorIndicadores = new KartPickList(null);
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
	
    public boolean alterarDados() {
    	clickTabela = false;
    	
        setModal(true);
        setVisible(true);
        
        return clickTabela; 
    }
	
    public String getNumeroKart() {
        return String.valueOf(numeroKart);
    }
	
}
