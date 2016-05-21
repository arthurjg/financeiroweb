package financeiro.web.filter;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.*;

import org.apache.log4j.Logger;

import financeiro.util.EntityManagerFactorySingleton;

public class ConexaoHibernateFilter implements Filter {
	
	private static Logger log = Logger.getLogger(ConexaoHibernateFilter.class);	
	private EntityManager manager;	

	@Override
	public void destroy() {
		log.info("encerrando filtro de conexão com Hibernate");		
		this.manager = null;		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {		
		try {
			this.manager.getTransaction().begin();			
			chain.doFilter(request, response);
			this.manager.getTransaction().commit();			
		} catch (Throwable ex) {
			try {
				if ( this.manager.getTransaction().isActive() ){
					this.manager.getTransaction().rollback();
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new ServletException(ex);
		}
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {	
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		manager = emf.createEntityManager();
		log.info("iniciando filtro de conexão com Hibernate");				
	}

}
