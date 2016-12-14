package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidade.DetalheSecaoE;

public class DetalheSecaoP {
	
	private StringBuilder sb = new StringBuilder();
	
	public ArrayList<DetalheSecaoE> PesquisaDetalhes(int bateria) throws SQLException {
		ArrayList<DetalheSecaoE> lista = new ArrayList<DetalheSecaoE>();
		Connection conexao = ConexaoP.getConexao();
		PreparedStatement comando;

		sb.setLength(0);
		sb.append("select d.bateria,");
		sb.append("       d.numerocpf,");
		sb.append("       trim(c.nome) as nome,");
		sb.append("       k.numerokart,");
		sb.append("       trim(k.categoria) as categoria,");
		sb.append("       k.especial");
		sb.append("  from detalhe_secao d left join cliente c");
		sb.append("    on d.numerocpf  = c.numerocpf");
		sb.append("  left join kart k");
		sb.append("    on d.numerokart = k.numerokart");
		sb.append(" where d.bateria    = ?");
		comando = conexao.prepareStatement(sb.toString());
		comando.setInt(1, bateria);
		ResultSet dados = comando.executeQuery();
		
		while(dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	public ArrayList<DetalheSecaoE> PesquisaDetalhesSecoes(int bateria) throws SQLException {
		ArrayList<DetalheSecaoE> lista = new ArrayList<DetalheSecaoE>();
		Connection conexao = ConexaoP.getConexao();
		PreparedStatement comando;
		
		sb.setLength(0);
		sb.append("select bateria,");
		sb.append("       numerocpf,");
		sb.append("       numerokart");
		sb.append("  from detalhe_secao");
		sb.append(" where bateria = ?");
		sb.append(" order by bateria, numerocpf, numerokart");
		comando = conexao.prepareStatement(sb.toString());
		comando.setInt(1, bateria);
		ResultSet dados = comando.executeQuery();
		
		while(dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	private DetalheSecaoE Instanciar(ResultSet dados) throws SQLException {
		DetalheSecaoE secao = new DetalheSecaoE();
		
		secao.bateria = dados.getInt("bateria");
		secao.numeroCPF = dados.getString("numerocpf");
		secao.nome = dados.getString("nome");
		secao.numeroKart = dados.getInt("numerokart");
		secao.categoria = dados.getString("categoria");
		secao.especial = dados.getBoolean("especial");
		
		return secao;
	}
	
	public void Incluir(DetalheSecaoE detalheSecao) throws SQLException {
		PreparedStatement comando;
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("insert into detalhe_secao");
		sb.append("           (bateria,");
		sb.append("            numerocpf,");
		sb.append("            numerokart)");
		sb.append(" values ");
		sb.append("           (?,");
		sb.append("            ?,");
		sb.append("            ?)");
		comando = conexao.prepareStatement(sb.toString());
		comando.setInt(1, detalheSecao.bateria);
		comando.setString(2, detalheSecao.numeroCPF);
		comando.setInt(3, detalheSecao.numeroKart);
		comando.executeUpdate();
		
		comando.close();
	}
	
	public void Excluir(DetalheSecaoE detalheSecao) throws SQLException {
		Connection conexao = ConexaoP.getConexao();

		sb.setLength(0);
		sb.append("delete from detalhe_secao");
		sb.append("      where bateria    = ?");
		sb.append("        and numerocpf  = ?");
		sb.append("        and numerokart = ?");
		PreparedStatement comando = conexao.prepareStatement(sb.toString());
		comando.setInt(1, detalheSecao.bateria);
		comando.setString(2, detalheSecao.numeroCPF);
		comando.setInt(3, detalheSecao.numeroKart);
		comando.executeUpdate();
		
		comando.close();
	}
	
}