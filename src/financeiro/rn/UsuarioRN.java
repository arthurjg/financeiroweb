package financeiro.rn;

import java.util.List;

import javax.ejb.Stateless;

import financeiro.dao.UsuarioDAO;
import financeiro.model.Usuario;
import financeiro.util.DAOFactory;

@Stateless
public class UsuarioRN {
	
	private UsuarioDAO usuarioDAO;
	private CategoriaRN categoriaRN;

	public UsuarioRN() {		
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
	}
	
	public UsuarioRN(UsuarioDAO usuarioDAO) {		
		this.usuarioDAO = usuarioDAO;
	}
	
	public UsuarioRN(UsuarioDAO usuarioDAO, CategoriaRN categoriaRN) {		
		this.usuarioDAO = usuarioDAO;
		this.categoriaRN = categoriaRN;
	}
	
	public Usuario carregar( Integer codigo ){
		return this.usuarioDAO.carregar(codigo);
	}
	
	public Usuario buscarPorLogin( String login ){
		return this.usuarioDAO.buscarPorLogin(login);
	}
	
	public Usuario buscarPorEmail( String email ){
		return this.usuarioDAO.buscarPorEmail(email);
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
		categoriaRN.excluir(usuario);
		this.usuarioDAO.excluir(usuario);
	}
	
	public List<Usuario> listar(){
		return this.usuarioDAO.listar();
	}	

}
