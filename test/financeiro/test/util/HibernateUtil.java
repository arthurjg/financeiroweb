package financeiro.test.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import financeiro.model.Usuario;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	static{
		try {
			AnnotationConfiguration cfg = new AnnotationConfiguration()
			.addAnnotatedClass(Usuario.class)
			.setProperty("dialect", "org.hibernate.dialect.DB2Dialect")
			.setProperty("current_session_context_class", "thread")
			.setProperty("hibernate.hbm2ddl.auto", "update")
			.setProperty("hibernate.connection.driver_class", "com.ibm.db2.jcc.DB2Driver")
			.setProperty("hibernate.connection.url", "jdbc:db2://localhost:50000/Finance")
			.setProperty("hibernate.connection.username", "Art")
			.setProperty("hibernate.connection.password", "artgar");
			
			sessionFactory = cfg.buildSessionFactory();
		} catch ( Throwable e) {
			System.out.println(
				"Criação inicial do objeto SessionFactory falhou. Erro: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public Session getSessionFactory(){
		return sessionFactory.openSession();
	}

}
