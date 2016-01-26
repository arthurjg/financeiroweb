package financeiro.usuario;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class UsuarioDAOHibernate implements UsuarioDAO {

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(Usuario usuario) {
		// TODO Auto-generated method stub
		this.session.save(usuario);
	}
	
	@Override
	public void atualizar(Usuario usuario) {

		if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) {
			Usuario usuarioPermissao = this.carregar(usuario.getCodigo());
			usuario.setPermissao(usuarioPermissao.getPermissao());
			this.session.evict(usuarioPermissao);
		}

		this.session.update(usuario);
	}

	@Override
	public void excluir(Usuario usuario) {
		// TODO Auto-generated method stub
		this.session.delete(usuario);

	}

	@Override
	public Usuario carregar(Integer codigo) {
		// TODO Auto-generated method stub
		return (Usuario) this.session.get(Usuario.class, codigo);
	}

	@Override
	public Usuario buscaPorLogin(String login) {
		// TODO Auto-generated method stub
		String hql = "select u from Usuario u where u.login = :login";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("login", login);
		return (Usuario) consulta.uniqueResult();
	}

	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return this.session.createCriteria(Usuario.class).list();
	}

	@Override
	public Usuario buscaPorEmail(String email) {
		// TODO Auto-generated method stub
		String hql = "select u from Usuario u where u.email = :email";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("email", email );
		return (Usuario) consulta.uniqueResult();
	}

}
