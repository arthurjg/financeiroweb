package financeiro.repository;

import java.util.List;

import financeiro.model.Categoria;
import financeiro.model.Usuario;

public interface CategoriaRepository {
	
	public Categoria salvar(Categoria categoria);

	public void excluir(Categoria categoria);

	public Categoria carregar(Integer categoria);

	public List<Categoria> listar(Usuario usuario);

}
