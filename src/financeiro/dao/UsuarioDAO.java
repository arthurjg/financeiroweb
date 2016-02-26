package financeiro.dao;

import java.util.List;

import financeiro.model.Usuario;

public interface UsuarioDAO {
	
	public void salvar( Usuario usuario );
	public void atualizar( Usuario usuario );
	public void excluir( Usuario usuario );
	public Usuario carregar( Integer codigo );
	public Usuario buscaPorLogin( String login );
	public Usuario buscaPorEmail( String email );
	public List<Usuario> listar();

}
