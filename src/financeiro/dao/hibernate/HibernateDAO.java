package financeiro.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import financeiro.dao.OrmDAO;

public abstract class HibernateDAO implements OrmDAO {
	
	private SessionFactory sessionFactory;
	private Session session;
		
	public void iniciaTransacao(){
		if(!session.isOpen()){
			session = sessionFactory.openSession();
		}
		session.beginTransaction();
	}

	public void encerraTransacao(){
		session.getTransaction().commit();
		session.close();
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