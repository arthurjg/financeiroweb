package financeiro.rn;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;

import financeiro.dao.jdbc.UsuarioDAOJDBC;
import financeiro.model.Permissao;
import financeiro.model.Usuario;
import financeiro.repository.UsuarioRepository;
import financeiro.repository.hibernate.UsuarioRepositoryImpl;


public class UsuarioRN {
	 
	@Inject
	private UsuarioRepository usuarioRepo;
	
	private UsuarioDAOJDBC usuarioDAOJDBC;
	
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
	
	@PostConstruct
	private void init(){
		usuarioDAOJDBC = new UsuarioDAOJDBC();
	}
	
	@Transactional
	public Usuario carregar( Integer codigo ){
		return this.usuarioDAOJDBC.carregar(codigo);
	}
	
	@Transactional
	public Usuario buscarPorLogin( String login ){
		return this.usuarioDAOJDBC.buscarPorLogin(login);
	}
	
	@Transactional
	public Usuario buscarPorEmail( String email ){
		return this.usuarioDAOJDBC.buscarPorEmail(email);
	}
	
	@Transactional
	public void salvar( Usuario usuario ){
		Permissao permissao = new Permissao("ROLE_USUARIO");
		//todo
		usuario.getPermissoes().add(permissao);
		this.usuarioDAOJDBC.salvar(usuario);			
		this.categoriaRN.salvaEstruturaPadrao(usuario);
	}
	
	@Transactional
	public void atualizar( Usuario usuario ){
		this.usuarioDAOJDBC.atualizar(usuario);		
	}
	
	@Transactional
	public void excluir( Usuario usuario ){		
		this.categoriaRN.excluir(usuario);
		this.usuarioDAOJDBC.excluir(usuario);
	}
	
	@Transactional
	public List<Usuario> listar(){
		return this.usuarioDAOJDBC.listar();
	}	

}
