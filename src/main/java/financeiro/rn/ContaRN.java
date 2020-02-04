package financeiro.rn;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import financeiro.model.Conta;
import financeiro.model.Usuario;
import financeiro.repository.ContaRepository;


public class ContaRN {
	
	@Inject
	private ContaRepository contaRepository;

	public ContaRN() {		
	}

	public List<Conta> listar(Usuario usuario) {	
		List<Conta> lista = this.contaRepository.listar(usuario);
		return somarSaldoLista(lista);
	}

	public Conta carregar(Integer conta) {
		return this.contaRepository.carregar(conta);
	}

	public void salvar(Conta conta) {
		conta.setDataCadastro(new Date());
		this.contaRepository.salvar(conta);
	}

	public void excluir(Conta conta) {
		Conta contaPersistida = this.contaRepository.carregar(conta.getConta());
		if(contaPersistida != null){
			this.contaRepository.excluir(contaPersistida);
		}		
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
