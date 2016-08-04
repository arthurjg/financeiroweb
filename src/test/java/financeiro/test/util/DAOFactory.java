package financeiro.test.util;

import org.hibernate.SessionFactory;

import financeiro.dao.UsuarioDAO;
import financeiro.dao.hibernate.UsuarioDAOHibernate;


public class DAOFactory {
	
	public static UsuarioDAO criarUsuarioDAO(){
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		SessionFactory sf = HibernateUtil.getSessionFactory();
		usuarioDAO.setSessionFactory(sf);
		return usuarioDAO;		
	}

}
