package financeiro.rn;

import java.util.Date;
import java.util.List;

import financeiro.dao.ContaDAO;
import financeiro.model.Conta;
import financeiro.model.Usuario;
import financeiro.util.DAOFactory;

public class ContaRN {

	private ContaDAO contaDAO;

	public ContaRN() {
		this.contaDAO = DAOFactory.criarContaDAO();
	}

	public List<Conta> listar(Usuario usuario) {		
		return somarSaldoLista(this.contaDAO.listar(usuario));
	}

	public Conta carregar(Integer conta) {
		return this.contaDAO.carregar(conta);
	}

	public void salvar(Conta conta) {
		conta.setDataCadastro(new Date());
		this.contaDAO.salvar(conta);
	}

	public void excluir(Conta conta) {
		this.contaDAO.excluir(conta);
	}

	public void tornarFavorita(Conta contaFavorita) { 
		Conta conta = this.buscarFavorita(contaFavorita.getUsuario());
		if (conta != null) {
			conta.setFavorita(false);
			this.contaDAO.salvar(conta);
		}

		contaFavorita.setFavorita(true);
		this.contaDAO.salvar(contaFavorita);
	}

	public Conta buscarFavorita(Usuario usuario) {
		return this.contaDAO.buscarFavorita(usuario);
	}
	
	public List<Conta> somarSaldoLista(List<Conta> lista) {
		Conta contaTotal = new Conta();
		contaTotal.setDescricao("Total - ");
		long total = 0;
		for( Conta conta : lista){
			total += conta.getSaldoAtual();
		}
		contaTotal.setSaldoAtual(total);
		lista.add(contaTotal);
		return lista;
	}
}