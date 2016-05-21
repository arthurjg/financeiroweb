package financeiro.rn;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import financeiro.dao.repository.UsuarioRepository;
import financeiro.model.Usuario;

@Stateless
public class UsuarioRN {
	 
	@Inject
	private UsuarioRepository usuarioRepo;
	private CategoriaRN categoriaRN;

	public UsuarioRN() {			
	}
	
	public UsuarioRN(UsuarioRepository usuarioRepo) {		
		this.usuarioRepo = usuarioRepo;
	}
	
	public UsuarioRN(UsuarioRepository usuarioRepo, CategoriaRN categoriaRN) {		
		this.usuarioRepo = usuarioRepo;
		this.categoriaRN = categoriaRN;
	}
	
	public Usuario carregar( Integer codigo ){
		return this.usuarioRepo.carregar(codigo);
	}
	
	public Usuario buscarPorLogin( String login ){
		return this.usuarioRepo.buscarPorLogin(login);
	}
	
	public Usuario buscarPorEmail( String email ){
		return this.usuarioRepo.buscarPorEmail(email);
	}
	
	public void salvar( Usuario usuario ){
		Integer codigo = usuario.getCodigo();
		if ( codigo == null || codigo == 0 ){
			usuario.getPermissao().add("ROLE_USUARIO");
			this.usuarioRepo.salvar(usuario);
			if (categoriaRN == null){
				categoriaRN = new CategoriaRN();
			}
			categoriaRN.salvaEstruturaPadrao(usuario);
		} else {
			this.usuarioRepo.atualizar(usuario);
		}
	}
	
	public void excluir( Usuario usuario ){		
		categoriaRN.excluir(usuario);
		this.usuarioRepo.excluir(usuario);
	}
	
	public List<Usuario> listar(){
		return this.usuarioRepo.listar();
	}	

}
