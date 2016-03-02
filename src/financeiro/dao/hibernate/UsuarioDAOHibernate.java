package financeiro.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import financeiro.dao.UsuarioDAO;
import financeiro.model.Usuario;

public class UsuarioDAOHibernate extends HibernateDAO implements UsuarioDAO {	

	@Override
	public void salvar(Usuario usuario) {		
		getSession().save(usuario);
	}
	
	@Override
	public void atualizar(Usuario usuario) {		
		if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) {
			Usuario usuarioPermissao = this.carregar(usuario.getCodigo());
			usuario.setPermissao(usuarioPermissao.getPermissao());
			getSession().evict(usuarioPermissao);
		}

		getSession().update(usuario);
	}

	@Override
	public void excluir(Usuario usuario) {		
		getSession().delete(usuario);
	}

	@Override
	public Usuario carregar(Integer codigo) {		
		return (Usuario) getSession().get(Usuario.class, codigo);
	}

	@Override
	public Usuario buscarPorLogin(String login) {		
		String hql = "select u from Usuario u where u.login = :login";
		Query consulta = getSession().createQuery(hql);
		consulta.setString("login", login);
		return (Usuario) consulta.uniqueResult();
	}

	@Override
	public List<Usuario> listar() {		
		return getSession().createCriteria(Usuario.class).list();
	}

	@Override
	public Usuario buscarPorEmail(String email) {		
		String hql = "select u from Usuario u where u.email = :email";
		Query consulta = getSession().createQuery(hql);
		consulta.setString("email", email );
		return (Usuario) consulta.uniqueResult();
	}

}
