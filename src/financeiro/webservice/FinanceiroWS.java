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
package financeiro.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import financeiro.model.Conta;
import financeiro.model.Lancamento;
import financeiro.rn.ContaRN;
import financeiro.rn.LancamentoRN;

@WebService
public class FinanceiroWS {
	
	ContaRN contaRN;
	LancamentoRN lancamentoRN;
	
	public FinanceiroWS(){
		
	}

	@WebMethod
	public float saldo(@WebParam(name="conta") int conta, @WebParam(name="dataSaldo") Date data) {
		contaRN = new ContaRN(); 
		lancamentoRN = new LancamentoRN();
		Conta contaPesquisada = contaRN.carregar(new Integer(conta));
		Float saldo = lancamentoRN.saldo(contaPesquisada, data);
		return saldo.floatValue();
	}

	@WebMethod
	public List<LancamentoItem> extrato(@WebParam(name="conta") int conta, 
			@WebParam(name="de") Date de, @WebParam(name="ate") Date ate) {		 
		contaRN = new ContaRN(); 
		lancamentoRN = new LancamentoRN();
		List<LancamentoItem> retorno = new ArrayList<LancamentoItem>();
		LancamentoItem lancamentoItem = null;

		Conta contaPesquisada = contaRN.carregar(new Integer(conta));
		List<Lancamento> listaLancamentos = lancamentoRN.listar(contaPesquisada, de, ate);
		for (Lancamento lancamento : listaLancamentos) {
			lancamentoItem = new LancamentoItem();
			lancamentoItem.setData(lancamento.getData());
			lancamentoItem.setDescricao(lancamento.getDescricao());
			lancamentoItem.setValor(lancamento.getValor().floatValue());
			retorno.add(lancamentoItem);
		}
		return retorno;
	}
}
