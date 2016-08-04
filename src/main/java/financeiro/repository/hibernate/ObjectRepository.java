package financeiro.repository.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import financeiro.dao.OrmDAO;

public abstract class ObjectRepository implements OrmDAO {
	
	@PersistenceContext
	private EntityManager manager;	
	
	public void iniciaTransacao(){
		manager.getTransaction().begin();
	}
	
	public void encerraTransacao(){
		manager.getTransaction().commit();		
	}	

}
