package financeiro.test.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import financeiro.model.Categoria;
import financeiro.model.Cheque;
import financeiro.model.Conta;
import financeiro.model.Lancamento;
import financeiro.model.Usuario;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	static{
		try {
			AnnotationConfiguration cfg = new AnnotationConfiguration()
			.addAnnotatedClass(Usuario.class)
			.addAnnotatedClass(Categoria.class)
			.addAnnotatedClass(Conta.class)
			.addAnnotatedClass(Lancamento.class)
			.addAnnotatedClass(Cheque.class)
			.setProperty("dialect", "org.hibernate.dialect.PostgreSQLDialect")
			.setProperty("current_session_context_class", "thread")
			.setProperty("hibernate.hbm2ddl.auto", "update")
			.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
			.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/FinanceiroTeste")
			.setProperty("hibernate.connection.username", "postgres")
			.setProperty("hibernate.connection.password", "1234");
			
			sessionFactory = cfg.buildSessionFactory();
		} catch ( Throwable e) {
			System.out.println(
				"Criação inicial do objeto SessionFactory falhou. Erro: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
				//.openSession();
	}

}
