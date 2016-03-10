package financeiro.web.filter;

import java.io.IOException;

import javax.servlet.*;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import financeiro.util.HibernateUtil;

public class ConexaoHibernateFilter implements Filter {
	
	private static Logger log = Logger.getLogger(ConexaoHibernateFilter.class);
	private SessionFactory sf;

	@Override
	public void destroy() {
		log.info("encerrando filtro de conexão com Hibernate");		
		this.sf = null;		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {		
		try {
			this.sf.getCurrentSession().beginTransaction();			
			chain.doFilter(request, response);
			this.sf.getCurrentSession().getTransaction().commit();
			this.sf.getCurrentSession().close();
		} catch (Throwable ex) {
			try {
				if ( this.sf.getCurrentSession().getTransaction().isActive() ){
					this.sf.getCurrentSession().getTransaction().rollback();
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new ServletException(ex);
		}
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {		
		log.info("iniciando filtro de conexão com Hibernate");		
		this.sf = HibernateUtil.getSessionFactory();		
	}

}
