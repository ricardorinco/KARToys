package apresentacao;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import util.Icons;

@SuppressWarnings("serial")
public class Principal extends JFrame {
	
	private Icons icoFlag = new Icons();
	
	private JDialog clienteCadastro;
	private JDialog clientePesquisaPorNome;
	private JDialog clientePesquisaPorCidade;
	private JDialog clientePesquisaPorData;
	
	private JDialog kartCadastro;
	private JDialog kartPesquisaPorIndicadores;
	
	private JDialog secaoCadastro;
	private JDialog detalheSecaCadastro;
	
	private JMenuBar menu = new JMenuBar();
	
	private JMenu menuPrograma = new JMenu("Programa");
	private JMenuItem menuSair = new JMenuItem("Sair");	
	
	private JMenu menuClientes = new JMenu("Clientes");
	private JMenuItem menuInclusaoClientes = new JMenuItem("Cadastro");
	private JMenu subMenuPesquisasCliente = new JMenu("Pesquisas");
	private JMenuItem menuClientePesquisaPorNome = new JMenuItem("Por Nome");
	private JMenuItem menuClientePesquisaPorCidade = new JMenuItem("Por Cidade");
	private JMenuItem menuClientePesquisaPorData = new JMenuItem("Por Data");
	
	private JMenu menuKart = new JMenu("Karts");
	private JMenuItem menuInclusaoKarts = new JMenuItem("Cadastro");
	private JMenu subMenuPesquisasKart = new JMenu("Pesquisas");
	private JMenuItem menuKartPesquisaPorIndicadores = new JMenuItem("Por Indicadores");
	
	private JMenu menuSecao = new JMenu("Seções");
	private JMenuItem menuInclusaoSecos = new JMenuItem("Cadastro");
	private JMenuItem menuInclusaoBateria = new JMenuItem("Bateria");
	
	private JMenu menuAjuda = new JMenu("Ajuda");
	private JMenuItem menuSobre = new JMenuItem("Sobre");
	
	
	public Principal() {
		super("KARToys - Sistema de Gerenciamento de Kartódromo");
		
		desenhaTela();
		setIcon();
		setAtalhos();
		registraEventos();
		
		this.setJMenuBar(menu);
	}
	
	private void desenhaTela() {
		menu.add(menuPrograma);
		menuPrograma.add(menuSair);
		
		menu.add(menuClientes);		
		menuClientes.add(menuInclusaoClientes);
		menuClientes.add(new JSeparator());
		menuClientes.add(subMenuPesquisasCliente);
		subMenuPesquisasCliente.add(menuClientePesquisaPorNome);
		subMenuPesquisasCliente.add(menuClientePesquisaPorCidade);
		subMenuPesquisasCliente.add(menuClientePesquisaPorData);
		
		menu.add(menuKart);
		menuKart.add(menuInclusaoKarts);
		menuKart.add(new JSeparator());
		menuKart.add(subMenuPesquisasKart);
		subMenuPesquisasKart.add(menuKartPesquisaPorIndicadores);
		
		menu.add(menuSecao);
		menuSecao.add(menuInclusaoSecos);
		menuSecao.add(new JSeparator());
		menuSecao.add(menuInclusaoBateria);
		
		menu.add(menuAjuda);
		menuAjuda.add(menuSobre);
		
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
		
	private void setIcon() {
		setIconImage(icoFlag.getIcoFlag());
	}
	
	private void setAtalhos() {
		menuPrograma.setMnemonic('P');
		menuSair.setMnemonic('S');
		menuSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		
		menuClientes.setMnemonic('C');		
		subMenuPesquisasCliente.setMnemonic('e');
		menuInclusaoClientes.setMnemonic('a');
		menuInclusaoClientes.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.SHIFT_MASK));
		menuClientePesquisaPorNome.setMnemonic('N');
		menuClientePesquisaPorNome.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.SHIFT_MASK));
		menuClientePesquisaPorCidade.setMnemonic('C');
		menuClientePesquisaPorCidade.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.SHIFT_MASK));
		menuClientePesquisaPorData.setMnemonic('D');
		menuClientePesquisaPorData.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.SHIFT_MASK));
		
		menuKart.setMnemonic('K');
		subMenuPesquisasKart.setMnemonic('e');
		menuInclusaoKarts.setMnemonic('a');
		menuInclusaoKarts.setAccelerator(KeyStroke.getKeyStroke('K', InputEvent.SHIFT_MASK));
		menuKartPesquisaPorIndicadores.setMnemonic('I');
		menuKartPesquisaPorIndicadores.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.SHIFT_MASK));	
		
		menuSecao.setMnemonic('S');
		menuInclusaoSecos.setMnemonic('a');
		menuInclusaoSecos.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.SHIFT_MASK));
		menuInclusaoBateria.setMnemonic('B');
		menuInclusaoBateria.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.SHIFT_MASK));
		
		menuAjuda.setMnemonic('J');
		menuSobre.setMnemonic('o');
		menuSobre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
	}
	
	private void registraEventos() {
		
		menuInclusaoClientes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuInclusaoClientes_Click();
			}
		});
		
		menuInclusaoKarts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuInclusaoKarts_Click();
			}
		});
		
		menuClientePesquisaPorNome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuClientePesquisaPorNome_Click();
			}
		});
		
		menuClientePesquisaPorCidade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuClientePesquisaPorCidade_Click();
			}
		});
		
		menuClientePesquisaPorData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuClientePesquisaPorData_Click();
			}
		});
		
		menuKartPesquisaPorIndicadores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuKartPesquisaPorIndicadores_Click();
			}
		});
		
		menuInclusaoSecos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuInclusaoSecos_Click();
			}
		});

		menuInclusaoBateria.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuInclusaoBateria_Click();
			}
		});
		
		menuSobre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuSobre_Click();
			}
		});
		
		menuSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuSair_Click();
			}
		});
	}
	
	private void menuInclusaoClientes_Click() {
		if (clienteCadastro == null) {
			clienteCadastro = new ClienteCadastro(this);
		}
		clienteCadastro.setVisible(true);
		clienteCadastro.setResizable(false);		
		clienteCadastro.setLocationRelativeTo(this);
	}
	
	private void menuInclusaoKarts_Click() {
		if (kartCadastro == null) {
			kartCadastro = new KartCadastro(this);
		}
		kartCadastro.setVisible(true);
		kartCadastro.setResizable(false);
		kartCadastro.setLocationRelativeTo(this);
	}
			
	private void menuClientePesquisaPorNome_Click() {
		if (clientePesquisaPorNome == null) {
			clientePesquisaPorNome = new ClientePesquisaPorNome(this);
		}
		clientePesquisaPorNome.setVisible(true);
		clientePesquisaPorNome.setResizable(false);
		clientePesquisaPorNome.setLocationRelativeTo(this);
	}
	
	private void menuClientePesquisaPorCidade_Click() {
		if (clientePesquisaPorCidade == null) {
			clientePesquisaPorCidade = new ClientePesquisaPorCidade(this);
		}
		clientePesquisaPorCidade.setVisible(true);
		clientePesquisaPorCidade.setResizable(false);
		clientePesquisaPorCidade.setLocationRelativeTo(this);
	}
	
	private void menuClientePesquisaPorData_Click() {
		if (clientePesquisaPorData== null) {
			clientePesquisaPorData = new ClientePesquisaPorData(this);
		}
		clientePesquisaPorData.setVisible(true);
		clientePesquisaPorData.setResizable(false);
		clientePesquisaPorData.setLocationRelativeTo(this);
	}
	
	private void menuKartPesquisaPorIndicadores_Click() {
		if (kartPesquisaPorIndicadores == null) {
			kartPesquisaPorIndicadores = new KartPesquisaPorIndicadores(this);
		}
		kartPesquisaPorIndicadores.setVisible(true);
		kartPesquisaPorIndicadores.setResizable(false);
		kartPesquisaPorIndicadores.setLocationRelativeTo(this);
	}
	
	private void menuInclusaoSecos_Click() {
		if (secaoCadastro == null) {
			secaoCadastro = new SecaoCadastro(this);
		}
		secaoCadastro.setVisible(true);
		secaoCadastro.setResizable(false);
		secaoCadastro.setLocationRelativeTo(this);
	}
	
	private void menuInclusaoBateria_Click() {
		if (detalheSecaCadastro == null) {
			detalheSecaCadastro = new DetalheSecaoCadastro(this);
		}
		detalheSecaCadastro.setVisible(true);
		detalheSecaCadastro.setResizable(false);
		detalheSecaCadastro.setLocationRelativeTo(this);
	}
	
	private void menuSobre_Click() {
		ImageIcon icone = new ImageIcon(icoFlag.getIcoFlag());
		JOptionPane.showMessageDialog(this, "Sistema KARToys\n\n"
				+ "Equipe de Desenvolvimento\n"
				+ "Ferreira, Lucas: 1301140\n"
				+ "Paffaro, Renan: 1302588\n"
				+ "Rinco, Ricardo: 1301141\n"
				+ "Torres, Sérgio: 1300061\n\n"
				+ "Contato: kartoys@outlook.com", "Sobre", JOptionPane.INFORMATION_MESSAGE, icone);
	}
	
	private void menuSair_Click() {
		if (JOptionPane.showConfirmDialog(this, "Deseja sair do Sistema KARToys?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	private static void criarMostrarGUI() {
		JFrame janelaPrincipal = new Principal();
		janelaPrincipal.setVisible(true);
		janelaPrincipal.setMinimumSize(new Dimension(800, 600));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				criarMostrarGUI();
			}
		});
	}

}
