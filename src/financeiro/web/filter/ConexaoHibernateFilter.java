package financeiro.web.filter;

import java.io.IOException;

import javax.servlet.*;


import org.hibernate.SessionFactory;

import financeiro.util.HibernateUtil;

public class ConexaoHibernateFilter implements Filter {
	
	private SessionFactory sf;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		this.sf = HibernateUtil.getSessionFactory();
		
	}

}
