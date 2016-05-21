package financeiro.repository;

import java.util.List;

import financeiro.model.Usuario;

public interface UsuarioRepository {
	
	public void salvar( Usuario usuario );
	public void atualizar( Usuario usuario );
	public void excluir( Usuario usuario );
	public Usuario carregar( Integer codigo );
	public Usuario buscarPorLogin( String login );
	public Usuario buscarPorEmail( String email );
	public List<Usuario> listar();

}
