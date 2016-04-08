/*
 * Cï¿½digo-fonte do livro "Programaï¿½ï¿½o Java para a Web"
 * Autores: Dï¿½cio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - todos os direitos reservados
 *
 * LICENï¿½A: Este arquivo-fonte estï¿½ sujeito a Atribuiï¿½ï¿½o 2.5 Brasil, da licenï¿½a Creative Commons,
 * que encontra-se disponï¿½vel no seguinte endereï¿½o URI: http://creativecommons.org/licenses/by/2.5/br/
 * Se vocï¿½ nï¿½o recebeu uma cï¿½pia desta licenï¿½a, e nï¿½o conseguiu obtï¿½-la pela internet, por favor,
 * envie uma notificaï¿½ï¿½o aos seus autores para que eles possam enviï¿½-la para vocï¿½ imediatamente.
 *
 *
 * Source-code of "Programaï¿½ï¿½o Java para a Web" book
 * Authors: Dï¿½cio Heinzelmann Luckow <decioluckow@gmail.com>
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

import java.util.ArrayList;
import java.util.List;

import financeiro.dao.CategoriaDAO;
import financeiro.model.Categoria;
import financeiro.model.Usuario;
import financeiro.util.DAOFactory;

@Stateless
public class CategoriaRN {

	private CategoriaDAO	categoriaDAO;

	public CategoriaRN() {
		this.categoriaDAO = DAOFactory.criarCategoriaDAO();
	}

	public CategoriaRN(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}

	public List<Categoria> listar(Usuario usuario) {
		return this.categoriaDAO.listar(usuario);
	}

	public Categoria salvar(Categoria categoria) {
		Categoria pai = categoria.getPai();

		if (pai == null) {
			String msg = "A Categoria " + categoria.getDescricao() + " deve ter um pai definido";
			throw new IllegalArgumentException(msg);
		}

		boolean mudouFator = pai.getFator() != categoria.getFator();

		categoria.setFator(pai.getFator());
		categoria = this.categoriaDAO.salvar(categoria);

		if (mudouFator) {
			categoria = this.carregar(categoria.getCodigo());
			this.replicarFator(categoria, categoria.getFator());
		}

		return categoria;
	}

	private void replicarFator(Categoria categoria, int fator) {
		if (categoria.getFilhos() != null) {
			for (Categoria filho : categoria.getFilhos()) {
				filho.setFator(fator);
				this.categoriaDAO.salvar(filho);
				this.replicarFator(filho, fator);
			}
		}
	}

	public void excluir(Categoria categoria) {

		//OrcamentoRN orcamentoRN = new OrcamentoRN();
		//orcamentoRN.excluir(categoria);

		this.categoriaDAO.excluir(categoria);
	}
	
	public void excluir(Usuario usuario) {
		List<Categoria> lista = this.listar(usuario);
		for (Categoria categoria:lista) {
			this.categoriaDAO.excluir(categoria);
		}
	}

	public Categoria carregar(Integer categoria) {
		return this.categoriaDAO.carregar(categoria);
	}
	
	public List<Integer> carregarCodigos(Integer categoria) {
		List<Integer> codigos = new ArrayList<Integer>();
		
		Categoria c = this.carregar(categoria);
		this.extraiCodigos(codigos, c);
		
		return codigos;
	}
	
	private void extraiCodigos(List<Integer> codigos, Categoria categoria) {
		codigos.add(categoria.getCodigo());
		if (categoria.getFilhos() != null) {
			for (Categoria filho:categoria.getFilhos()) {
				this.extraiCodigos(codigos, filho);
			}
		}
	}

	public void salvaEstruturaPadrao(Usuario usuario) {

		Categoria despesas = new Categoria(null, usuario, "DESPESAS", -1);
		despesas = this.categoriaDAO.salvar(despesas);
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Moradia", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Alimentação", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Vestuário", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Deslocamento", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Cuidados Pessoais", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Educação", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Saúde", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Lazer", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Despesas Financeiras", -1));

		Categoria receitas = new Categoria(null, usuario, "RECEITAS", 1);
		receitas = this.categoriaDAO.salvar(receitas);
		this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Salário", 1));
		this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Restituições", 1));
		this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Rendimento", 1));
	}
}