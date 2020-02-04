/*
 * C�digo-fonte do livro "Programa��o Java para a Web"
 * Autores: D�cio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - todos os direitos reservados
 *
 * LICEN�A: Este arquivo-fonte est� sujeito a Atribui��o 2.5 Brasil, da licen�a Creative Commons,
 * que encontra-se dispon�vel no seguinte endere�o URI: http://creativecommons.org/licenses/by/2.5/br/
 * Se voc� n�o recebeu uma c�pia desta licen�a, e n�o conseguiu obt�-la pela internet, por favor,
 * envie uma notifica��o aos seus autores para que eles possam envi�-la para voc� imediatamente.
 *
 *
 * Source-code of "Programa��o Java para a Web" book
 * Authors: D�cio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - all rights reserved
 *
 * LICENSE: This source file is subject to Attribution version 2.5 Brazil of the Creative Commons
 * license that is available through the following URI:  http://creativecommons.org/licenses/by/2.5/br/
 * If you did not receive a copy of this license and are unable to obtain it through the web, please
 * send a note to the authors so they can mail you a copy immediately.
 *
 */
package financeiro.repository.hibernate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import financeiro.model.Conta;
import financeiro.model.Lancamento;
import financeiro.repository.LancamentoRepository;


public class LancamentoRepositoryImpl implements LancamentoRepository {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager manager;	

	public void salvar(Lancamento lancamento) {
		manager.persist(lancamento);
	}
	
	public void atualizar(Lancamento lancamento) {
		manager.merge(lancamento);
	}

	public void excluir(Lancamento lancamento) {
		manager.remove(lancamento);
	}

	public Lancamento carregar(Integer lancamento) {
		return (Lancamento)  manager.find(Lancamento.class, lancamento);
	}
	
	@Override
	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) {		

		StringBuffer sql = new StringBuffer();
		sql.append("select lan");
		sql.append("  from Lancamento lan ");		
		sql.append("	join fetch lan.categoria");
		sql.append(" where lan.conta = :conta");		
		
		if (dataInicio != null && dataFim != null) {
			sql.append("   and lan.data >= :dataInicio");
			sql.append("   and lan.data <= :dataFim");
		} else if (dataInicio != null) {			
			sql.append("   and lan.data >= :dataInicio");
		} else if (dataFim != null) {
			sql.append("   and lan.data <= :dataFim");	
		}	
		sql.append(" order by lan.data");

		Query query = manager.createQuery(sql.toString());

		query.setParameter("conta", conta);
		if (dataInicio != null) {			
			query.setParameter("dataInicio", dataInicio);
		} 
		if (dataFim != null) {
			query.setParameter("dataFim", dataFim);	
		}				
		
		return query.getResultList();	
	}

	public float saldo(Conta conta, Date data) {

		if (data == null) {
			throw new IllegalArgumentException("[Financeiro] data cannot be null");
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select sum(l.valor * c.fator)");
		sql.append("  from Lancamento l,");
		sql.append("	     Categoria c");
		sql.append(" where l.categoria = c.codigo");
		sql.append("   and l.conta = :conta");
		sql.append("   and l.data <= :data");

		Query query = manager.createQuery(sql.toString());

		query.setParameter("conta", conta);
		query.setParameter("data", data);

		BigDecimal saldo = (BigDecimal) query.getSingleResult();

		if (saldo != null) {
			return saldo.floatValue();
		}
		return 0f;
	}
	
	public List<Lancamento> listarCriteria(Conta conta, Date dataInicio, Date dataFim) {		
		
		CriteriaBuilder criteria = manager.getCriteriaBuilder(); 
		CriteriaQuery<Lancamento> criteriaQuery = criteria.createQuery(Lancamento.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		criteriaQuery.select(root);	
		
		Predicate predicateDate = null;		
		
		ParameterExpression<Long> dataInicioParam = criteria.parameter(Long.class, "dataInicio");		
		ParameterExpression<Long> dataFimParam = criteria.parameter(Long.class, "dataFim");
		Path<Date> dataPath = root.<Date>get("data");
		
		if (dataInicio != null && dataFim != null) {
			predicateDate = criteria.between(dataPath.as(Long.class), dataInicioParam, dataFimParam);			
		} else if (dataInicio != null) {			
			predicateDate = criteria.ge(dataPath.as(Long.class), dataInicioParam);
		} else if (dataFim != null) {
			predicateDate = criteria.le(dataPath.as(Long.class), dataFimParam);			
		}		
		
		if(predicateDate != null){
			Predicate predicate = criteria.and(predicateDate, criteria.equal(root.get("conta"), conta));			
			criteriaQuery.where(predicate);	
		}
		
		criteriaQuery.orderBy(criteria.asc(root.get("data")));
		
		TypedQuery<Lancamento> query = manager.createQuery(criteriaQuery);	
		
		if(dataInicio != null){
			query.setParameter("dataInicio", (Long)dataInicio.getTime());
		}
		if(dataFim != null){
			query.setParameter("dataFim", (Long)dataFim.getTime());
		}		
		
		return query.getResultList();	
	}
}
