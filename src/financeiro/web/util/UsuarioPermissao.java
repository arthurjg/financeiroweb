package financeiro.web.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import financeiro.model.Usuario;


public class UsuarioPermissao extends User {	
	
	private static final long serialVersionUID = 1L;
	private Usuario usuario;

	public UsuarioPermissao(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {		
		super(usuario.getLogin(), usuario.getSenha(), usuario.isAtivo(), true, true, true, authorities);		
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
