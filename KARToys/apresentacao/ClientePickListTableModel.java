package apresentacao;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entidade.ClienteE;

@SuppressWarnings("serial")
public class ClientePickListTableModel extends AbstractTableModel {
	
	private String[] colunas = new String[]{"Número CPF", "Nome"};
	private ArrayList<ClienteE> lista = new ArrayList<ClienteE>();

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return lista == null ? 0 : lista.size();
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		ClienteE cliente = lista.get(linha);
		
		switch(coluna) {
			case 0: return cliente.numeroCPF;
			case 1: return cliente.nome;
		}
		return null;
	}
	
	@Override
	public String getColumnName(int coluna) {
		return colunas[coluna];
	}
	
	@Override
	public Class<?> getColumnClass(int colunaIndex) {		
		switch (colunaIndex) {
		case 0: return Long.class;
		case 1: return String.class;
		}
		return null;
	}
	
	public ClienteE getValueAt(int linha) {
		return lista.get(linha);
	}
	
	public void setResultSet(ArrayList<ClienteE> novaLista) {
		if (novaLista != null) {
			lista = novaLista;
		} else {
			lista = new ArrayList<ClienteE>();
		}
		
		fireTableChanged(null); 
	}

	public void limpar() {
		lista.clear();
		
		fireTableDataChanged();
	}
	
	public boolean isEmpty() {
		return lista.isEmpty();
	}

}

