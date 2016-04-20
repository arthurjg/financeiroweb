package financeiro.rn;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import financeiro.dao.repository.ContaRepository;
import financeiro.model.Conta;
import financeiro.model.Usuario;

@Stateless
public class ContaRN {
	
	@Inject
	private ContaRepository contaRepository;

	public ContaRN() {		
	}

	public List<Conta> listar(Usuario usuario) {		
		return somarSaldoLista(this.contaRepository.listar(usuario));
	}

	public Conta carregar(Integer conta) {
		return this.contaRepository.carregar(conta);
	}

	public void salvar(Conta conta) {
		conta.setDataCadastro(new Date());
		this.contaRepository.salvar(conta);
	}

	public void excluir(Conta conta) {
		this.contaRepository.excluir(conta);
	}

	public void tornarFavorita(Conta contaFavorita) { 
		Conta conta = this.buscarFavorita(contaFavorita.getUsuario());
		if (conta != null) {
			conta.setFavorita(false);
			this.contaRepository.salvar(conta);
		}

		contaFavorita.setFavorita(true);
		this.contaRepository.salvar(contaFavorita);
	}

	public Conta buscarFavorita(Usuario usuario) {
		return this.contaRepository.buscarFavorita(usuario);
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
