package financeiro.util;

import org.hibernate.Session;

import financeiro.conta.*;
import financeiro.usuario.*;


public class DAOFactory {
	
	public static UsuarioDAO criarUsuarioDAO(){
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		usuarioDAO.setSession(session);
		return usuarioDAO;		
	}
	
	public static ContaDAO criarContaDAO(){
		ContaDAOHibernate contaDAO = new ContaDAOHibernate();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		contaDAO.setSession(session);
		return contaDAO;		
	}

}
