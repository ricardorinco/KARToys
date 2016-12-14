package apresentacao;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entidade.DetalheSecaoE;

@SuppressWarnings("serial")
public class DetalheSecaoTableModel extends AbstractTableModel {
	
	private String[] colunas = new String[]{"Bateria", "CPF", "Nome", "Kart",
			                                "Cat.", "Esp."};
	private ArrayList<DetalheSecaoE> lista = new ArrayList<DetalheSecaoE>();

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
		DetalheSecaoE detalheSecao = lista.get(linha);
		
		switch(coluna) {
			case 0: return detalheSecao.bateria;
			case 1: return detalheSecao.numeroCPF;
			case 2: return detalheSecao.nome;
			case 3: return detalheSecao.numeroKart;
			case 4: return detalheSecao.categoria;
			case 5: return detalheSecao.especial;
		}
		return null;
	}
	
	@Override
	public String getColumnName(int coluna) {
		return colunas[coluna];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {		
		switch (columnIndex) {
		case 0: return Integer.class;
		case 1: return String.class;
		case 2: return String.class;
		case 3: return Integer.class;
		case 4: return String.class;
		case 5: return Boolean.class;
		}
		return null;
	}
	
	public DetalheSecaoE getValueAt(int linha) {
		return lista.get(linha);
	}

	public void setResultSet(ArrayList<DetalheSecaoE> novaLista) {
		if (novaLista != null) {
			lista = novaLista;
		} else {
			lista = new ArrayList<DetalheSecaoE>();
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