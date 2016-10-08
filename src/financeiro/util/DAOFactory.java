package financeiro.util;

import org.hibernate.Session;

import financeiro.dao.AcaoDAO;
import financeiro.dao.CategoriaDAO;
import financeiro.dao.ChequeDAO;
import financeiro.dao.ContaDAO;
import financeiro.dao.LancamentoDAO;
import financeiro.dao.UsuarioDAO;
import financeiro.dao.hibernate.AcaoDAOHibernate;
import financeiro.dao.hibernate.CategoriaDAOHibernate;
import financeiro.dao.hibernate.ChequeDAOHibernate;
import financeiro.dao.hibernate.ContaDAOHibernate;
import financeiro.dao.hibernate.LancamentoDAOHibernate;
import financeiro.dao.hibernate.UsuarioDAOHibernate;


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
	
	public static CategoriaDAO criarCategoriaDAO(){
		CategoriaDAOHibernate categoriaDAO = new CategoriaDAOHibernate();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		categoriaDAO.setSession(session);
		return categoriaDAO;		
	}

	public static LancamentoDAO criarLancamentoDAO() {
		LancamentoDAOHibernate lancamentoDAO = new LancamentoDAOHibernate();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		lancamentoDAO.setSession(session);
		return lancamentoDAO;
	}
	
	public static ChequeDAO criarChequeDAO() {
		ChequeDAOHibernate chequeDAO = new ChequeDAOHibernate();
		chequeDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return chequeDAO;
	}
	
	public static AcaoDAO criarAcaoDAO() {
		AcaoDAOHibernate acaoDAO = new AcaoDAOHibernate();
		acaoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return acaoDAO;
	}
	
	

}
