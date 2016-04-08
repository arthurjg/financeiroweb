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
package financeiro.rn;

import java.util.Date;
import java.util.List;

import financeiro.dao.ChequeDAO;
import financeiro.model.Cheque;
import financeiro.model.ChequeId;
import financeiro.model.Conta;
import financeiro.model.Lancamento;
import financeiro.util.DAOFactory;
import financeiro.util.RNException;

@Stateless
public class ChequeRN {

	private ChequeDAO	chequeDAO;

	public ChequeRN() {
		this.chequeDAO = DAOFactory.criarChequeDAO();
	}

	public void salvar(Cheque cheque) {
		this.chequeDAO.salvar(cheque);
	}

	public int salvarSequencia(Conta conta, Integer chequeInicial, Integer chequeFinal) {
		Cheque cheque = null;
		Cheque chequeVerifica = null;
		ChequeId chequeId = null;
		int contaTotal = 0;

		for (int i = chequeInicial; i <= chequeFinal; i++) {
			chequeId = new ChequeId();
			chequeId.setCheque(i);
			chequeId.setConta(conta.getConta().intValue());
			chequeVerifica = this.carregar(chequeId);

			if (chequeVerifica == null) {
				cheque = new Cheque();
				cheque.setChequeId(chequeId);
				cheque.setSituacao(Cheque.SITUACAO_CHEQUE_NAO_EMITIDO);
				cheque.setDataCadastro(new Date(System.currentTimeMillis()));
				this.salvar(cheque);
				contaTotal++;
			}
		}
		return contaTotal;
	}

	public void excluir(Cheque cheque) throws RNException {
		if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_NAO_EMITIDO) {
			this.chequeDAO.excluir(cheque);
		} else {
			throw new RNException("N�o � poss�vel excluir cheque, status n�o permitido para opera��o.");
		}
	}

	public Cheque carregar(ChequeId chequeId) {
		return this.chequeDAO.carregar(chequeId);
	}

	public List<Cheque> listar(Conta conta) {
		return this.chequeDAO.listar(conta);
	}

	public void cancelarCheque(Cheque cheque) throws RNException {
		if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_NAO_EMITIDO || cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_CANCELADO) {
			cheque.setSituacao(Cheque.SITUACAO_CHEQUE_CANCELADO);
			this.chequeDAO.salvar(cheque);
		} else {
			throw new RNException("N�o � poss�vel cancelar cheque, status n�o permitido para opera��o.");
		}
	}

	public void baixarCheque(ChequeId chequeId, Lancamento lancamento) {
		Cheque cheque = this.carregar(chequeId);
		if (cheque != null) {
			cheque.setSituacao(Cheque.SITUACAO_CHEQUE_BAIXADO);
			cheque.setLancamento(lancamento);
			this.chequeDAO.salvar(cheque);
		}
	}

	public void desvinculaLancamento(Conta conta, Integer numeroCheque) throws RNException {
		ChequeId chequeId = new ChequeId();
		chequeId.setCheque(numeroCheque);
		chequeId.setConta(conta.getConta().intValue());
		Cheque cheque = this.carregar(chequeId);
		if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_CANCELADO) {
			throw new RNException("N�o � poss�vel usar cheque cancelado.");	
		} else {
			cheque.setSituacao(Cheque.SITUACAO_CHEQUE_NAO_EMITIDO);
			cheque.setLancamento(null);
			this.salvar(cheque);
		}
	}
}
