package financeiro.repository.hibernate;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Query;
import org.hibernate.Session;

import financeiro.model.Categoria;
import financeiro.model.Conta;
import financeiro.model.Usuario;
import financeiro.repository.CategoriaRepository;

@Stateless
public class CategoriaRepositoryImpl implements CategoriaRepository {
	
	@PersistenceContext
	private EntityManager manager;	
	
	public CategoriaRepositoryImpl() {		
	}	

	//TODO VERIFICAR NECESSIDADE NA JPA
	@Override
	public Categoria salvar(Categoria categoria) {
		manager.persist(categoria);
		Categoria merged = categoria;		
		return merged;
	}	

	@Override
	public void excluir(Categoria categoria) {
		categoria = (Categoria) this.carregar(categoria.getCodigo());
		this.manager.remove(categoria);		
	}

	@Override
	public Categoria carregar(Integer categoria) {
		return (Categoria) this.manager.find(Categoria.class, categoria);
	}
	
	//TODO FUNCIONA?
	@Override
	public List<Categoria> listar(Usuario usuario) {

		//String hql = "select c from Categoria c where c.pai is null and c.usuario = :usuario";		
		CriteriaBuilder criteria = manager.getCriteriaBuilder(); 
		CriteriaQuery<Categoria> criteriaQuery = criteria.createQuery(Categoria.class);
		Root<Categoria> root = criteriaQuery.from(Categoria.class);
		criteriaQuery.select(root);		
		
		Predicate predicate = criteria.isNull(root.get("pai"));
		Predicate predicate2 = criteria.equal(root.get("usuario"), usuario);
		criteriaQuery.where(predicate).where(predicate2);
		
		TypedQuery<Categoria> query = manager.createQuery(criteriaQuery);
		List<Categoria> categorias = query.getResultList();		

		return categorias;		
	}

}
