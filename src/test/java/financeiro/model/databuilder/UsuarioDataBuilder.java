package financeiro.model.databuilder;

import financeiro.model.Usuario;

public class UsuarioDataBuilder {
	
	private Usuario usuario;
	
	public UsuarioDataBuilder(){
		usuario = new Usuario();
	}
	
	public Usuario getUsuario(){
		return this.usuario;
	}
	
	public UsuarioDataBuilder setNome(String nome){
		this.usuario.setNome(nome);
		return this;
	}
	
	public UsuarioDataBuilder setLogin(String login){
		this.usuario.setLogin(login);
		return this;
	}
	
	public UsuarioDataBuilder setEmail(String email){
		this.usuario.setEmail(email);
		return this;
	}

}
