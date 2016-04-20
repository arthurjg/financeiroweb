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
package financeiro.rn;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import financeiro.dao.repository.LancamentoRepository;
import financeiro.model.Conta;
import financeiro.model.Lancamento;

@Stateless
public class LancamentoRN {
	
	@Inject
	private LancamentoRepository	lancamentoRepository;

	public LancamentoRN() {		
	}

	public void salvar(Lancamento lancamento) {
		this.lancamentoRepository.salvar(lancamento);
	}

	public void excluir(Lancamento lancamento) {
		this.lancamentoRepository.excluir(lancamento);
	}

	public Lancamento carregar(Integer lancamento) {
		return this.lancamentoRepository.carregar(lancamento);
	}

	public float saldo(Conta conta, Date data) { 
		float saldoInicial = conta.getSaldoInicial();
		float saldoNaData = this.lancamentoRepository.saldo(conta, data);
		return saldoInicial + saldoNaData;
	}

	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) { 
		return this.lancamentoRepository.listar(conta, dataInicio, dataFim);
	}
}
