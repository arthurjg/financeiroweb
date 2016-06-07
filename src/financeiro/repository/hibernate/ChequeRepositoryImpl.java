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

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import financeiro.model.Cheque;
import financeiro.model.ChequeId;
import financeiro.model.Conta;
import financeiro.repository.ChequeRepository;

@Stateless
public class ChequeRepositoryImpl implements ChequeRepository {

	@PersistenceContext
	private EntityManager manager;	

	public ChequeRepositoryImpl() {			
	}

	@Override
	public void salvar(Cheque cheque) {
		this.manager.persist(cheque);
	}

	@Override
	public void excluir(Cheque cheque) {
		if(!this.manager.contains(cheque)){
			cheque = this.manager.merge(cheque);
		}
		this.manager.remove(cheque);
	}

	@Override
	public Cheque carregar(ChequeId chequeId) {
		return (Cheque) this.manager.find(Cheque.class, chequeId);
	}
	
	@Override
	public List<Cheque> listar(Conta conta) {		
		
		CriteriaBuilder criteria = manager.getCriteriaBuilder(); 
		CriteriaQuery<Cheque> criteriaQuery = criteria.createQuery(Cheque.class);
		Root<Cheque> root = criteriaQuery.from(Cheque.class);
		criteriaQuery.select(root);	
		
		Predicate predicate = criteria.equal(root.get("conta"), conta);		
		criteriaQuery.where(predicate);
		
		TypedQuery<Cheque> query = manager.createQuery(criteriaQuery);
		List<Cheque> cheques = query.getResultList();	
		
		return cheques;
	}
}
