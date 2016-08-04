package financeiro.dao;

import java.util.List;

import financeiro.model.Conta;
import financeiro.model.Usuario;

public interface ContaDAO {

	public void salvar(Conta conta);

	public void excluir(Conta conta);

	public Conta carregar(Integer conta);

	public List<Conta> listar(Usuario usuario);

	public Conta buscarFavorita(Usuario usuario);
	
}
