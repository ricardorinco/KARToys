package persistencia;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidade.ClienteE;

public class ClienteP {
	
	private StringBuilder sb = new StringBuilder();
	
	public ArrayList<ClienteE> PesquisaPickList(String nome) throws SQLException {
		ArrayList<ClienteE> lista = new ArrayList<ClienteE>();
		Connection conexao = ConexaoP.getConexao();
		PreparedStatement comando;
		
		sb.setLength(0);
		sb.append("select *");
		sb.append("  from cliente");
		sb.append(" where nome like ?");
		sb.append(" order by nome");
		comando = conexao.prepareStatement(sb.toString());
		comando.setString(1, nome + "%");
		ResultSet dados = comando.executeQuery();
		
		while (dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	public boolean PesquisaCPF(String cpf) throws SQLException {
		Connection conexao = ConexaoP.getConexao();
		PreparedStatement comando;
		
		sb.setLength(0);
		sb.append("select numerocpf");
		sb.append("  from cliente");
		sb.append(" where numerocpf = ?");
		comando = conexao.prepareStatement(sb.toString());
		comando.setString(1, cpf);
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
	
	public ArrayList<ClienteE> PesquisaPorCPF(String cpf) throws SQLException {	
		ArrayList<ClienteE> lista = new ArrayList<ClienteE>();
		Connection conexao = ConexaoP.getConexao();
		PreparedStatement comando;
		
		sb.setLength(0);
		sb.append("select numerocpf,");
		sb.append("       trim(nome) as nome,");
		sb.append("       sexo,");
		sb.append("       datanascimento,");
		sb.append("       trim(endereco) as endereco,");
		sb.append("       numero,");
		sb.append("       trim(bairro) as bairro,");
		sb.append("       trim(cidade) as cidade,");
		sb.append("       uf,");
		sb.append("       cep,");
		sb.append("       telefonefixo,");
		sb.append("       telefonecelular,");
		sb.append("       datacadastro");
		sb.append("  from cliente");
		sb.append(" where numerocpf = ?");
		sb.append(" order by nome");
		comando = conexao.prepareStatement(sb.toString());
		comando.setString(1, cpf);
		ResultSet dados = comando.executeQuery();
		
		while(dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
		
	public ArrayList<ClienteE> PesquisaCompleta() throws SQLException {
		ArrayList<ClienteE> lista = new ArrayList<ClienteE>();
		Connection conexao = ConexaoP.getConexao();

		sb.setLength(0);
		sb.append("select numerocpf,");
		sb.append("       trim(nome) as nome,");
		sb.append("       sexo,");
		sb.append("       datanascimento,");
		sb.append("       trim(endereco) as endereco,");
		sb.append("       numero,");
		sb.append("       trim(bairro) as bairro,");
		sb.append("       trim(cidade) as cidade,");
		sb.append("       uf,");
		sb.append("       cep,");
		sb.append("       telefonefixo,");
		sb.append("       telefonecelular,");
		sb.append("       datacadastro");
		sb.append("  from cliente");
		sb.append(" order by nome");
		ResultSet dados = conexao.createStatement().executeQuery(sb.toString());
		
		while (dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	public ArrayList<ClienteE> PesquisaCompletaPorNome(String nome) throws SQLException {
		ArrayList<ClienteE> lista = new ArrayList<ClienteE>();
		Connection conexao = ConexaoP.getConexao();
		PreparedStatement comando;
		
		sb.setLength(0);
		sb.append("select numerocpf,");
		sb.append("       trim(nome) as nome,");
		sb.append("       sexo,");
		sb.append("       datanascimento,");
		sb.append("       trim(endereco) as endereco,");
		sb.append("       numero,");
		sb.append("       trim(bairro) as bairro,");
		sb.append("       trim(cidade) as cidade,");
		sb.append("       uf,");
		sb.append("       cep,");
		sb.append("       telefonefixo,");
		sb.append("       telefonecelular,");
		sb.append("       datacadastro");
		sb.append("  from cliente");
		sb.append(" where nome like ?");
		sb.append(" order by nome");
		comando = conexao.prepareStatement(sb.toString());
		comando.setString(1, nome + "%");
		ResultSet dados = comando.executeQuery();
		
		while (dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	public ArrayList<ClienteE> PesquisaCompletaPorCidade(String cidade) throws SQLException {
		ArrayList<ClienteE> lista = new ArrayList<ClienteE>();
		Connection conexao = ConexaoP.getConexao();
		PreparedStatement comando;
		
		sb.setLength(0);
		sb.append("select numerocpf,");
		sb.append("       trim(nome) as nome,");
		sb.append("       sexo,");
		sb.append("       datanascimento,");
		sb.append("       trim(endereco) as endereco,");
		sb.append("       numero,");
		sb.append("       trim(bairro) as bairro,");
		sb.append("       trim(cidade) as cidade,");
		sb.append("       uf,");
		sb.append("       cep,");
		sb.append("       telefonefixo,");
		sb.append("       telefonecelular,");
		sb.append("       datacadastro");
		sb.append("  from cliente");
		sb.append(" where cidade like ?");
		sb.append(" order by cidade");
		comando = conexao.prepareStatement(sb.toString());
		comando.setString(1, cidade + "%");
		ResultSet dados = comando.executeQuery();
		
		while (dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
		
	public ArrayList<ClienteE> PesquisaCompletaPorDataNascimento(Date dataInicial, Date dataFinal, boolean tipo) throws SQLException {
		ArrayList<ClienteE> lista = new ArrayList<ClienteE>();
		PreparedStatement comando;
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("select numerocpf,");
		sb.append("       trim(nome) as nome,");
		sb.append("       sexo,");
		sb.append("       datanascimento,");
		sb.append("       trim(endereco) as endereco,");
		sb.append("       numero,");
		sb.append("       trim(bairro) as bairro,");
		sb.append("       trim(cidade) as cidade,");
		sb.append("       uf,");
		sb.append("       cep,");
		sb.append("       telefonefixo,");
		sb.append("       telefonecelular,");
		sb.append("       datacadastro");
		sb.append("  from cliente");
		sb.append(" where " + (tipo ? "datanascimento" : "datacadastro") + " between ? and ?");
		sb.append(" order by " + (tipo ? "datanascimento" : "datacadastro"));
		comando = conexao.prepareStatement(sb.toString());
		comando.setDate(1, new java.sql.Date(dataInicial.getTime()));
		comando.setDate(2, new java.sql.Date(dataFinal.getTime()));
		ResultSet dados = comando.executeQuery();

		while (dados.next()) {
			lista.add(Instanciar(dados));
		}
		
		conexao.close();
		return lista;
	}
	
	private ClienteE Instanciar(ResultSet dados) throws SQLException {
		ClienteE cliente = new ClienteE();
		
		cliente.numeroCPF = dados.getString("numerocpf");
		cliente.nome = dados.getString("nome");
		cliente.sexo = dados.getString("sexo");
		cliente.dataNascimento = dados.getDate("datanascimento");
		cliente.endereco = dados.getString("endereco");
		cliente.numero = dados.getString("numero");
		cliente.bairro = dados.getString("bairro");
		cliente.cidade = dados.getString("cidade");
		cliente.uf = dados.getString("uf");
		cliente.cep = dados.getString("cep");
		cliente.telefoneFixo = dados.getString("telefonefixo");
		cliente.telefoneCelular = dados.getString("telefonecelular");
		cliente.dataCadastro = dados.getDate("datacadastro");
		
		return cliente;
	}

	public void Incluir(ClienteE cliente) throws SQLException {
		PreparedStatement comando;
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("insert into cliente");
		sb.append("           (numerocpf,");
		sb.append("            nome,");
		sb.append("            sexo,");
		sb.append("            datanascimento,");
		sb.append("            endereco,");
		sb.append("            numero,");
		sb.append("            bairro,");
		sb.append("            cidade,");
		sb.append("            uf,");
		sb.append("            cep,");
		sb.append("            telefoneFixo,");
		sb.append("            telefoneCelular,");
		sb.append("            datacadastro)");
		sb.append(" value ");
		sb.append("           (?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?,");
		sb.append("            ?)");
		comando = conexao.prepareStatement(sb.toString());
		comando.setString(1, cliente.numeroCPF);
		comando.setString(2, cliente.nome);
		comando.setString(3, cliente.sexo);
		comando.setDate(4, new java.sql.Date(cliente.dataNascimento.getTime()));
		comando.setString(5, cliente.endereco);
		comando.setString(6, cliente.numero);
		comando.setString(7, cliente.bairro);
		comando.setString(8, cliente.cidade);
		comando.setString(9, cliente.uf);
		comando.setString(10, cliente.cep);
		comando.setString(11, cliente.telefoneFixo);
		comando.setString(12, cliente.telefoneCelular);
		comando.setDate(13, new java.sql.Date(cliente.dataCadastro.getTime()));
		comando.executeUpdate();
		
		comando.close();
	}
	
	public void Alterar(ClienteE cliente) throws SQLException {
		Connection conexao = ConexaoP.getConexao();
		
		sb.setLength(0);
		sb.append("update cliente set");
		sb.append("       nome            = ?,");
		sb.append("       sexo            = ?,");
		sb.append("       datanascimento  = ?,");
		sb.append("       endereco        = ?,");
		sb.append("       numero          = ?,");
		sb.append("       bairro          = ?,");
		sb.append("       cidade          = ?,");
		sb.append("       uf              = ?,");
		sb.append("       cep             = ?,");
		sb.append("       telefonefixo    = ?,");
		sb.append("       telefonecelular = ?,");
		sb.append("       datacadastro    = ?");
		sb.append(" where numerocpf       = ?");
		PreparedStatement comando = conexao.prepareStatement(sb.toString());
		comando.setString(1, cliente.nome);
		comando.setString(2, cliente.sexo);
		comando.setDate(3, new java.sql.Date(cliente.dataNascimento.getTime()));
		comando.setString(4, cliente.endereco);
		comando.setString(5, cliente.numero);
		comando.setString(6, cliente.bairro);
		comando.setString(7, cliente.cidade);
		comando.setString(8, cliente.uf);
		comando.setString(9, cliente.cep);
		comando.setString(10, cliente.telefoneFixo);
		comando.setString(11, cliente.telefoneCelular);
		comando.setDate(12, new java.sql.Date(cliente.dataCadastro.getTime()));
		comando.setString(13, cliente.numeroCPF);
		comando.executeUpdate();
		
		comando.close();
	}
		
}