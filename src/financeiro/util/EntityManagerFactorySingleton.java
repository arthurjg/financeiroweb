package financeiro.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {
	
	private static EntityManagerFactory instance;
	
	public static EntityManagerFactory getInstance(){
		if(instance == null){
			instance = Persistence.createEntityManagerFactory("financeiroPU");
		}
		return instance;
	}

}
