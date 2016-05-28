package financeiro.rn;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.transaction.Transactional;

import financeiro.model.Usuario;
import financeiro.repository.UsuarioRepository;
import financeiro.repository.hibernate.UsuarioRepositoryImpl;

@Stateful
public class UsuarioRN {
	 
	@Inject
	private UsuarioRepository usuarioRepo;
	
	@Inject
	private CategoriaRN categoriaRN;

	public UsuarioRN() {			
	}
	
	public UsuarioRN(UsuarioRepository usuarioRepo) {		
		this.usuarioRepo = usuarioRepo;
	}
	
	public UsuarioRN(UsuarioRepositoryImpl usuarioRepo, CategoriaRN categoriaRN) {		
		this.usuarioRepo = usuarioRepo;
		this.categoriaRN = categoriaRN;
	}
	
	@Transactional
	public Usuario carregar( Integer codigo ){
		return this.usuarioRepo.carregar(codigo);
	}
	
	@Transactional
	public Usuario buscarPorLogin( String login ){
		return this.usuarioRepo.buscarPorLogin(login);
	}
	
	@Transactional
	public Usuario buscarPorEmail( String email ){
		return this.usuarioRepo.buscarPorEmail(email);
	}
	
	@Transactional
	public void salvar( Usuario usuario ){
		usuario.getPermissao().add("ROLE_USUARIO");
		this.usuarioRepo.salvar(usuario);			
		this.categoriaRN.salvaEstruturaPadrao(usuario);
	}
	
	@Transactional
	public void atualizar( Usuario usuario ){
		this.usuarioRepo.atualizar(usuario);
	}
	
	@Transactional
	public void excluir( Usuario usuario ){		
		this.categoriaRN.excluir(usuario);
		this.usuarioRepo.excluir(usuario);
	}
	
	@Transactional
	public List<Usuario> listar(){
		return this.usuarioRepo.listar();
	}	

}
