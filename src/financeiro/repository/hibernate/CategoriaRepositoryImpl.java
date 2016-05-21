package financeiro.repository.hibernate;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import financeiro.model.Categoria;
import financeiro.model.Usuario;
import financeiro.repository.CategoriaRepository;

@Stateless
public class CategoriaRepositoryImpl implements CategoriaRepository {
	
	@PersistenceContext
	private EntityManager manager;
	private Session	session;
	
	public CategoriaRepositoryImpl() {
		this.session = manager.unwrap(Session.class);	
	}	

	@Override
	public Categoria salvar(Categoria categoria) {
		Categoria merged = (Categoria) this.session.merge(categoria);
		this.session.flush();
		this.session.clear();
		return merged;
	}	

	@Override
	public void excluir(Categoria categoria) {
		categoria = (Categoria) this.carregar(categoria.getCodigo());
		this.session.delete(categoria);
		this.session.flush();
		this.session.clear();
	}

	@Override
	public Categoria carregar(Integer categoria) {
		return (Categoria) this.session.get(Categoria.class, categoria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> listar(Usuario usuario) {

		String hql = "select c from Categoria c where c.pai is null and c.usuario = :usuario";
		Query query = this.session.createQuery(hql);
		query.setInteger("usuario", usuario.getCodigo());

		List<Categoria> lista = query.list();

		return lista;
	}

}
