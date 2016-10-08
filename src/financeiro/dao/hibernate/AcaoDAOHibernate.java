/*
 * Código-fonte do livro "Programação Java para a Web"
 * Autores: Décio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - todos os direitos reservados
 *
 * LICENÇA: Este arquivo-fonte está sujeito a Atribuição 2.5 Brasil, da licença Creative Commons,
 * que encontra-se disponível no seguinte endereço URI: http://creativecommons.org/licenses/by/2.5/br/
 * Se você não recebeu uma cópia desta licença, e não conseguiu obtê-la pela internet, por favor,
 * envie uma notificação aos seus autores para que eles possam enviá-la para você imediatamente.
 *
 *
 * Source-code of "Programação Java para a Web" book
 * Authors: Décio Heinzelmann Luckow <decioluckow@gmail.com>
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
package financeiro.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import financeiro.dao.AcaoDAO;
import financeiro.model.Acao;
import financeiro.model.Usuario;

public class AcaoDAOHibernate implements AcaoDAO {

	private Session	session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(Acao acao) {
		this.session.saveOrUpdate(acao);
	}

	@Override
	public void excluir(Acao acao) {
		this.session.delete(acao);
	}

	@Override
	public Acao carregar(String codigo) {
		return (Acao) this.session.get(Acao.class, codigo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Acao> listar(Usuario usuario) {
		Criteria criteria = this.session.createCriteria(Acao.class);
		criteria.add(Restrictions.eq("usuario", usuario));
		return criteria.list();
	}
}
