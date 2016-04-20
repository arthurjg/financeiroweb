package financeiro.web.filter;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.*;

import org.apache.log4j.Logger;

public class ConexaoHibernateFilter implements Filter {
	
	private static Logger log = Logger.getLogger(ConexaoHibernateFilter.class);
	@PersistenceContext
	private EntityManager manager;	

	@Override
	public void destroy() {
		log.info("encerrando filtro de conex�o com Hibernate");		
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
		log.info("iniciando filtro de conex�o com Hibernate");				
	}

}
