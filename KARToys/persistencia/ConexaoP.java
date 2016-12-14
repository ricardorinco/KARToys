package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoP {
	
	private static String url = "jdbc:mysql://localhost/kartoys";
	private static Connection conexao;
	
	private ConexaoP() {}
	
	public static Connection getConexao() throws SQLException {
		if(conexao == null || conexao.isClosed()) {
			conexao = DriverManager.getConnection(url, "root", "");
		}
		return conexao;
	}
	
}
