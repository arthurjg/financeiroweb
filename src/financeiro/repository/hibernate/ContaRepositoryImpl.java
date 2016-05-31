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

import financeiro.model.Conta;
import financeiro.model.Usuario;
import financeiro.repository.ContaRepository;

@Stateless
public class ContaRepositoryImpl implements ContaRepository {

	@PersistenceContext
	private EntityManager manager;	

	@Override
	public void excluir(Conta conta) {
		manager.merge(conta);
		manager.remove(conta);
	}

	@Override
	public void salvar(Conta conta) {
		manager.persist(conta);
	}

	@Override
	public Conta carregar(Integer conta) {		
		return (Conta) manager.find(Conta.class, conta);
	}
	
	@Override
	public List<Conta> listar(Usuario usuario) {

		CriteriaBuilder criteria = manager.getCriteriaBuilder(); 
		CriteriaQuery<Conta> criteriaQuery = criteria.createQuery(Conta.class);
		Root<Conta> root = criteriaQuery.from(Conta.class);
		criteriaQuery.select(root);
		
		Predicate predicate = criteria.equal(root.get("usuario"), usuario);
		criteriaQuery.where(predicate);
		
		TypedQuery<Conta> query = manager.createQuery(criteriaQuery);
		List<Conta> contas = query.getResultList();		

		return contas;		
	}

	@Override
	public Conta buscarFavorita(Usuario usuario) {
		
		CriteriaBuilder criteria = manager.getCriteriaBuilder(); 
		CriteriaQuery<Conta> criteriaQuery = criteria.createQuery(Conta.class);
		Root<Conta> root = criteriaQuery.from(Conta.class);
		criteriaQuery.select(root);
		
		Predicate predicate = criteria.and(
				criteria.equal(root.get("favorita"), true),
				criteria.equal(root.get("usuario"), usuario));
		criteriaQuery.where(predicate);		
		
		TypedQuery<Conta> query = manager.createQuery(criteriaQuery);		
		
		Conta contaFavorita = query.getSingleResult();	

		return contaFavorita;		
	}
}

