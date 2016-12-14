package apresentacao;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entidade.KartE;

@SuppressWarnings("serial")
public class KartTableModel extends AbstractTableModel {
	
	private String[] colunas = new String[]{"Númeração", "Cat.", "Especial", "Manut.", "Ativo"};
	private ArrayList<KartE> lista = new ArrayList<KartE>();

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
		KartE kart = lista.get(linha);
		
		switch(coluna) {
			case 0: return kart.numerokart;
			case 1: return kart.categoria;
			case 2: return kart.especial;
			case 3: return kart.manutencao;
			case 4: return kart.ativo;
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
		case 2: return Boolean.class;
		case 3: return Boolean.class;
		case 4: return Boolean.class;
		}
		return null;
	}
	
	public KartE getValueAt(int linha) {
		return lista.get(linha);
	}

	public void setResultSet(ArrayList<KartE> novaLista) {
		if (novaLista != null) {
			lista = novaLista;
		} else {
			lista = new ArrayList<KartE>();
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