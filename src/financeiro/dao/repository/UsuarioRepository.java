package financeiro.dao.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



import javax.persistence.Query;

import financeiro.dao.UsuarioDAO;
import financeiro.model.Usuario;

@Stateless
public class UsuarioRepository extends ObjectRepository implements UsuarioDAO {	

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void salvar(Usuario usuario) {		
		manager.persist(usuario);
	}
	
	@Override
	public void atualizar(Usuario usuario) {		
		if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) {
			Usuario usuarioPermissao = this.carregar(usuario.getCodigo());
			usuario.setPermissao(usuarioPermissao.getPermissao());			
			manager.detach(usuarioPermissao);
		}
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

	@Override
	public Usuario buscarPorLogin(String login) {		
		String hql = "select u from Usuario u where u.login = :login";
		Query consulta = manager.createQuery(hql);
		consulta.setParameter("login", login);
		return (Usuario) consulta.getSingleResult();
	}

	@Override
	public List<Usuario> listar() {		
		String hql = "select u from Usuario u";
		Query consulta = manager.createQuery(hql);
		return (List<Usuario>) consulta.getResultList();
	}

	@Override
	public Usuario buscarPorEmail(String email) {		
		String hql = "select u from Usuario u where u.email = :email";
		Query consulta = manager.createQuery(hql);
		consulta.setParameter("email", email );
		return (Usuario) consulta.getSingleResult();
	}
	
	public void setManager(EntityManager em){
		this.manager = em;
		
	}

}
