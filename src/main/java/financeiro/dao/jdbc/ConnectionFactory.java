package financeiro.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import financeiro.util.UtilException;

public class ConnectionFactory {
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:postgresql://localhost:5434/Financeiro", "postgres", "1234");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Connection getDSConnection() throws UtilException {
		java.sql.Connection conexao = null;
		try {
			Context initContext = new InitialContext();			
			javax.sql.DataSource ds = (javax.sql.DataSource) initContext.lookup("java:jboss/datasources/FinanceiroDS");
			conexao = (java.sql.Connection) ds.getConnection();
		} catch (NamingException e) {
			throw new UtilException("Não foi possível encontrar o nome da conexão do banco.", e);
		} catch (SQLException e) {
			throw new UtilException("Ocorreu um erro de SQL.", e);
		}
		return conexao;
	}

}
