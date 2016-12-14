package apresentacao;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entidade.ClienteE;

@SuppressWarnings("serial")
public class ClienteTableModel extends AbstractTableModel {
	
	private String[] colunas = new String[]{"Número CPF", "Nome", "Sexo",
			                                "Data Nasc.", "Endereço", "Número",
			                                "Bairro", "Cidade", "UF", "CEP", 
			                                "Telefone", "Celular", "Data Cad."};
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
			case 2: return cliente.sexo;
			case 3: return cliente.dataNascimento;
			case 4: return cliente.endereco;
			case 5: return cliente.numero;
			case 6: return cliente.bairro;
			case 7: return cliente.cidade;
			case 8: return cliente.uf;
			case 9: return cliente.cep;
			case 10: return cliente.telefoneFixo;
			case 11: return cliente.telefoneCelular;
			case 12: return cliente.dataCadastro;
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
		case 2: return String.class;
		case 3: return Date.class;
		case 4: return String.class;
		case 5: return String.class;
		case 6: return String.class;
		case 7: return String.class;
		case 8: return String.class;
		case 9: return String.class;
		case 10: return String.class;
		case 11: return String.class;
		case 12: return Date.class;
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

