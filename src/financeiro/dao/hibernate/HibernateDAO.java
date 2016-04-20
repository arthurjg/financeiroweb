package financeiro.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import financeiro.dao.OrmDAO;

public abstract class HibernateDAO implements OrmDAO {
	
	@PersistenceContext
	private EntityManager manager;
	private SessionFactory sessionFactory;
	private Session session;
	
	public void iniciaTransacao(){
		manager.getTransaction().begin();
	}
	
	public void encerraTransacao(){
		manager.getTransaction().commit();		
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Session getSession() {
		return this.session;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.session = sessionFactory.openSession();
	}

}
