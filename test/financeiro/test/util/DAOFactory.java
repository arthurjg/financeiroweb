package financeiro.test.util;

import org.hibernate.Session;

import financeiro.dao.UsuarioDAO;
import financeiro.dao.hibernate.UsuarioDAOHibernate;


public class DAOFactory {
	
	public static UsuarioDAO criarUsuarioDAO(){
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		Session session = new HibernateUtil().getSessionFactory();
		usuarioDAO.setSession(session);
		return usuarioDAO;		
	}

}
