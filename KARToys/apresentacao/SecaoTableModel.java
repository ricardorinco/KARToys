package apresentacao;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entidade.SecaoE;

@SuppressWarnings("serial")
public class SecaoTableModel extends AbstractTableModel {
	
	private String[] colunas = new String[]{"Seção", "Data", "Hora", "Cat."};
	private ArrayList<SecaoE> lista = new ArrayList<SecaoE>();

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
		SecaoE secao = lista.get(linha);
		
		switch(coluna) {
			case 0: return secao.bateria;
			case 1: return secao.dataBateria;
			case 2: return secao.horario;
			case 3: return secao.categoria;
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
		case 1: return Date.class;
		case 2: return String.class;
		case 3: return String.class;
		}
		return null;
	}
	
	public SecaoE getValueAt(int linha) {
		return lista.get(linha);
	}

	public void setResultSet(ArrayList<SecaoE> novaLista) {
		if (novaLista != null) {
			lista = novaLista;
		} else {
			lista = new ArrayList<SecaoE>();
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