package financeiro.repository.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import financeiro.model.Usuario;
import financeiro.repository.UsuarioRepository;


public class UsuarioRepositoryImpl implements UsuarioRepository {	

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager manager;
	
	@Override
	public void salvar(Usuario usuario) {		
		manager.persist(usuario);
	}
	
	@Override
	public void atualizar(Usuario usuario) {		
		if (usuario.getPermissoes() == null || usuario.getPermissoes().size() == 0) {
			Usuario usuarioPermissao = this.carregar(usuario.getCodigo());
			usuario.setPermissoes(usuarioPermissao.getPermissoes());			
			manager.detach(usuarioPermissao);
		}
		manager.merge(usuario);
		//persist?
	}

	@Override
	public void excluir(Usuario usuario) {		
		manager.remove(usuario);
	}

	@Override
	public Usuario carregar(Integer codigo) {		
		return (Usuario) manager.find(Usuario.class, codigo);
	}

	/**
	 * Utilizado join fetch para impedir erros de lazy loading 
	 */
	@Override
	public Usuario buscarPorLogin(String login) {		
		String hql = "select u from Usuario u join fetch u.permissao where u.login = :login";
		Query consulta = manager.createQuery(hql);
		consulta.setParameter("login", login);
		return (Usuario) consulta.getSingleResult();
	}

	@Override
	public List<Usuario> listar() {		
		String hql = "select u from Usuario u";
		Query consulta = manager.createQuery(hql);
		return consulta.getResultList();
	}

	@Override
	public Usuario buscarPorEmail(String email) {		
		String hql = "select u from Usuario u where u.email = :email";
		Query consulta = manager.createQuery(hql);
		consulta.setParameter("email", email );
		return (Usuario) consulta.getSingleResult();
	}	

}
