package financeiro.rn;

import java.util.List;

import financeiro.dao.CategoriaDAO;
import financeiro.dao.UsuarioDAO;
import financeiro.model.Usuario;
import financeiro.util.DAOFactory;

public class UsuarioRN {
	
	private UsuarioDAO usuarioDAO;
	private CategoriaRN categoriaRN;

	public UsuarioRN() {		
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
	}
	
	public UsuarioRN(UsuarioDAO usuarioDAO) {		
		this.usuarioDAO = usuarioDAO;
	}
	
	public UsuarioRN(UsuarioDAO usuarioDAO, CategoriaDAO categoriaDAO) {		
		this.usuarioDAO = usuarioDAO;
		this.categoriaRN = new CategoriaRN(categoriaDAO);
	}
	
	public Usuario carregar( Integer codigo ){
		return this.usuarioDAO.carregar(codigo);
	}
	
	public Usuario buscarPorLogin( String login ){
		return this.usuarioDAO.buscaPorLogin(login);
	}
	
	public Usuario buscarPorEmail( String email ){
		return this.usuarioDAO.buscaPorEmail(email);
	}
	
	public void salvar( Usuario usuario ){
		Integer codigo = usuario.getCodigo();
		if ( codigo == null || codigo == 0 ){
			usuario.getPermissao().add("ROLE_USUARIO");
			this.usuarioDAO.salvar(usuario);
			if (categoriaRN == null){
				categoriaRN = new CategoriaRN();
			}
			categoriaRN.salvaEstruturaPadrao(usuario);
		} else {
			this.usuarioDAO.atualizar(usuario);
		}
	}
	
	public void excluir( Usuario usuario ){
		CategoriaRN categoriaRN = new CategoriaRN();
		categoriaRN.excluir(usuario);
		this.usuarioDAO.excluir(usuario);
	}
	
	public List<Usuario> listar(){
		return this.usuarioDAO.listar();
	}	

}
