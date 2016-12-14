package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import entidade.SecaoE;

public class SecaoP {
	
	private StringBuilder sb = new StringBuilder();
	
	public ArrayList<SecaoE> PesquisaPorBateria(Date date, String horario) throws SQLException {
		ArrayList<SecaoE> lista = new ArrayList<SecaoE>();
		Connection conexao = ConexaoP.getConexao();
		PreparedStatement comando;
		
		sb.setLength(0);
		sb.append("select bateria,");
		sb.append("       databateria,");
		sb.append("       date_format(horario, '%H:%i') as horario,");
		sb.append("       categoria");
		sb.append("  from secao");
		sb.append(" where databateria = ?");
		sb.append("   and horario     = ?");
		comando = conexao.prepareStatement(sb.toString());
		comando.setDate(1, new java.sql.Date(date.getTime()));
		comando.setString(2, horario);
		ResultSet dados = comando.executeQuery();
		
		while(dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	public ArrayList<SecaoE> PesquisaSecoes() throws SQLException {
		ArrayList<SecaoE> lista = new ArrayList<SecaoE>();
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("select bateria,");
		sb.append("       databateria,");
		sb.append("       date_format(horario, '%H:%i') as horario,");
		sb.append("       categoria");
		sb.append("  from secao");
		sb.append(" order by databateria, horario");
		ResultSet dados = conexao.createStatement().executeQuery(sb.toString());
		
		while(dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	private SecaoE Instanciar(ResultSet dados) throws SQLException {
		SecaoE secao = new SecaoE();
		
		secao.bateria = dados.getInt("bateria");
		secao.dataBateria = dados.getDate("databateria");
		secao.horario = dados.getString("horario");
		secao.categoria = dados.getString("categoria");
		
		return secao;
	}
	
	public int Incluir(SecaoE secao) throws SQLException {
		PreparedStatement comando;
		Connection conexao = ConexaoP.getConexao();
		
		int proximaBateria = 0;
		sb.setLength(0);
		sb.append("select max(bateria) as proxima");
		sb.append("  from secao");
		comando = conexao.prepareStatement(sb.toString());
		ResultSet dados = comando.executeQuery();
		if (dados.next()) {
			proximaBateria = dados.getInt("proxima");
		}
		proximaBateria++;

		sb.setLength(0);
		sb.append("insert into secao");
		sb.append("           (bateria,");
		sb.append("            databateria,");
		sb.append("            horario,");
		sb.append("            categoria)");
		sb.append(" values ");
		sb.append("           (?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?)");
		comando = conexao.prepareStatement(sb.toString());
		comando.setInt(1, proximaBateria);
		comando.setDate(2, new java.sql.Date(secao.dataBateria.getTime()));
		comando.setString(3, secao.horario);
		comando.setString(4, secao.categoria.equals("A") ? "A" : "P");
		comando.executeUpdate();
		
		comando.close();
		
		return proximaBateria;
	}
	
	public void Alterar(SecaoE secao) throws SQLException {
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("update secao set");
		sb.append("       databateria   = ?,");
		sb.append("       and horario   = ?");
		sb.append("       and categoria = ?");
		sb.append(" where bateria       = ?");
		PreparedStatement comando = conexao.prepareStatement(sb.toString());
		comando.setDate(1, new java.sql.Date(secao.dataBateria.getTime()));
		comando.setString(2, secao.horario);
		comando.setString(3, secao.categoria.equals("A") ? "A" : "P");
		comando.setInt(4, secao.bateria);
		comando.executeUpdate();
		
		comando.close();
	}
	
}