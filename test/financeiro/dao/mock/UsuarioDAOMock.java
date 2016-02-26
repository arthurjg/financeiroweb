package financeiro.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import financeiro.dao.UsuarioDAO;
import financeiro.model.Usuario;

public class UsuarioDAOMock implements UsuarioDAO {
	
	public static Map<Integer, Usuario> usuarios = new HashMap<Integer, Usuario>();
	public static Integer currentId = 0;

	@Override
	public void salvar(Usuario usuario) {
		currentId++;
		usuarios.put(currentId, usuario);
	}

	@Override
	public void atualizar(Usuario usuario) {
		usuarios.put(usuario.getCodigo(), usuario);
	}

	@Override
	public void excluir(Usuario usuario) {
		usuarios.remove(usuario.getCodigo());
	}

	@Override
	public Usuario carregar(Integer codigo) {
		return usuarios.get(codigo);
	}

	@Override
	public Usuario buscaPorLogin(String login) {
		for ( Usuario usuario : usuarios.values() ){
			if(usuario.getLogin().equals(login)){
				return usuario;
			}
		}
		return null;
	}

	@Override
	public Usuario buscaPorEmail(String email) {
		for ( Usuario usuario : usuarios.values() ){
			if(usuario.getEmail().equals(email)){
				return usuario;
			}
		}
		return null;
	}

	@Override
	public List<Usuario> listar() {		
		return new ArrayList<Usuario>(usuarios.values()) ;
	}

}
