package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidade.KartE;

public class KartP {
	
	private StringBuilder sb = new StringBuilder();
	
	public ArrayList<KartE> PesquisaPickList(boolean condicao) throws SQLException {
		ArrayList<KartE> lista = new ArrayList<KartE>();
		PreparedStatement comando;
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("select *");
		sb.append("  from kart");
		if (condicao) {
			sb.append(" where especial   = ?");
			sb.append("   and manutencao = ?");
		} else {
			sb.append(" where manutencao = ?");
		}
		sb.append(" order by numerokart asc");
		comando = conexao.prepareStatement(sb.toString());
		if (condicao) {
			comando.setString(1, "S");
			comando.setString(2, "N");
		} else {
			comando.setString(1, "N");
		}
		ResultSet dados = comando.executeQuery();
		
		while(dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	public boolean PesquisaNumeroKart(int numerokart) throws SQLException {	
		Connection conexao = ConexaoP.getConexao();
		PreparedStatement comando;
		
		sb.setLength(0);
		sb.append("select numerokart");
		sb.append("  from kart");
		sb.append(" where numerokart = ?");
		sb.append("   and manutencao = ?");
		comando = conexao.prepareStatement(sb.toString());
		comando.setInt(1, numerokart);
		comando.setString(2, "N");
		ResultSet dados = comando.executeQuery();
		
		boolean registro = false;
		while(dados.next()) {
			registro = true;
		}
		
		if (registro) {
			conexao.close();
			return true;
		} else {
			conexao.close();
			return false;
		}
	}
	
	public ArrayList<KartE> PesquisaPorNumeroKart(int numerokart) throws SQLException {	
		ArrayList<KartE> lista = new ArrayList<KartE>();
		Connection conexao = ConexaoP.getConexao();
		PreparedStatement comando;
		
		sb.setLength(0);
		sb.append("select numerokart,");
		sb.append("       categoria,");
		sb.append("       especial,");
		sb.append("       manutencao,");
		sb.append("       ativo");
		sb.append("  from kart");
		sb.append(" where numerokart = ?");
		sb.append(" order by numerokart asc");
		comando = conexao.prepareStatement(sb.toString());
		comando.setInt(1, numerokart);
		ResultSet dados = comando.executeQuery();
		
		while(dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	public ArrayList<KartE> PesquisaCompleta() throws SQLException {
		ArrayList<KartE> lista = new ArrayList<KartE>();
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("select numerokart,");
		sb.append("       categoria,");
		sb.append("       especial,");
		sb.append("       manutencao,");
		sb.append("       ativo");
		sb.append("  from kart");
		sb.append(" order by numerokart");
		ResultSet dados = conexao.createStatement().executeQuery(sb.toString());
		
		while(dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	public ArrayList<KartE> PesquisaIndicadores(int condicao) throws SQLException {
		ArrayList<KartE> lista = new ArrayList<KartE>();
		PreparedStatement comando;
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("select numerokart,");
		sb.append("       categoria,");
		sb.append("       especial,");
		sb.append("       manutencao,");
		sb.append("       ativo");
		sb.append("  from kart");
		switch (condicao) {
		case 1:
			sb.append(" where manutencao = ?");
			break;
		case 2:
			sb.append(" where especial = ?");
			break;
		case 3:
			sb.append(" where ativo = ?");
			break;
		}
		sb.append(" order by numerokart asc");
		comando = conexao.prepareStatement(sb.toString());
		if (condicao > 0 && condicao < 4) {
			comando.setString(1, "S");
		}
		ResultSet dados = comando.executeQuery();
		
		while(dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	public KartE Instanciar(ResultSet dados) throws SQLException {
		KartE kart = new KartE();
		
		kart.numerokart = dados.getInt("numerokart");
		kart.categoria = dados.getString("categoria");
		kart.especial = dados.getString("especial").equals("S") ? true : false;
		kart.manutencao = dados.getString("manutencao").equals("S") ? true : false;
		kart.ativo = dados.getString("ativo").equals("S") ? true : false;
		
		return kart;
	}
	
	public void Incluir(KartE kart) throws SQLException {
		PreparedStatement comando;
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("insert into kart");
		sb.append("           (numerokart,");
		sb.append("            categoria,");
		sb.append("            especial,");
		sb.append("            manutencao,");
		sb.append("            ativo)");
		sb.append(" values ");
		sb.append("           (?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?)");
		comando = conexao.prepareStatement(sb.toString());
		comando.setInt(1, kart.numerokart);
		comando.setString(2, kart.categoria.equals("A") ? "A" : "P");
		comando.setString(3, kart.especial ? "S" : "N");
		comando.setString(4, kart.manutencao ? "S" : "N");
		comando.setString(5, kart.ativo ? "S" : "N");
		comando.executeUpdate();
		
		comando.close();
	}

	public void Alterar(KartE kart) throws SQLException {
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("update kart set");
		sb.append("            categoria = ?,");
		sb.append("            especial = ?,");
		sb.append("            manutencao = ?,");
		sb.append("            ativo = ?");
		sb.append("      where numerokart = ?");
		PreparedStatement comando = conexao.prepareStatement(sb.toString());
		comando.setString(1, kart.categoria.equals("A") ? "A" : "P");
		comando.setString(2, kart.especial ? "S" : "N");
		comando.setString(3, kart.manutencao ? "S" : "N");
		comando.setString(4, kart.ativo ? "S" : "N");
		comando.setInt(5, kart.numerokart);
		comando.executeUpdate();
		
		comando.close();
	}
	
}