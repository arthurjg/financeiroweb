package financeiro.web;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import financeiro.model.Conta;
import financeiro.model.Permissao;
import financeiro.model.Usuario;
import financeiro.rn.ContaRN;
import financeiro.rn.UsuarioRN;

@ManagedBean(name="usuarioBean")
@RequestScoped
public class UsuarioBean {
	
	@Inject
	private UsuarioRN usuarioRN;
	
	@Inject
	private ContaRN contaRN;
	
	private Usuario usuario;
	private String confirmarSenha;
	private List<Usuario> lista;
	private String destinoSalvar;	
	private Conta	       conta	= new Conta();
	
	public UsuarioBean(){
		this.usuario = new Usuario();
	}
	
	public String novo(){
		this.destinoSalvar = "usuarioSucesso";
		this.usuario = new Usuario();
		this.usuario.setAtivo(true);
		return "usuario";
	}
	
	public String editar() {
		this.confirmarSenha = this.usuario.getSenha();
		return "/publico/usuario";
	}
	
	public String salvar(){				
		
		if ( !validaSenha(this.usuario.getSenha()) ) {
			mostraMensagem("A senha não foi confirmada corretamente");			
		}
		
		boolean salvarNovo = this.usuario.getCodigo() == null || this.usuario.getCodigo() == 0;
		
		if(salvarNovo){
			if( usuarioRN.buscarPorLogin(this.usuario.getLogin()) != null ){
				mostraMensagem("Esse login j� est� sendo utilizado. Por favor informe outro.");
			} else {
				usuarioRN.salvar( this.usuario);
			}
		} else {
			usuarioRN.atualizar( this.usuario);
		}		
		
		if (this.conta.getDescricao() != null) {
			this.conta.setUsuario(this.usuario);
			this.conta.setFavorita(true);			
			contaRN.salvar(this.conta);
		}
		
		return this.destinoSalvar;
	}
	
	public String excluir() {		
		usuarioRN.excluir(this.usuario);
		this.lista = null;
		return null;
	}

	public String ativar() {
		if (this.usuario.isAtivo())
			this.usuario.setAtivo(false);
		else
			this.usuario.setAtivo(true);
		
		usuarioRN.salvar(this.usuario);
		return null;
	}
	
	public String atribuiPermissao(Usuario usuario, Permissao permissao) {

		this.usuario = usuario;
		List<Permissao> permissoes = this.usuario.getPermissoes();

		if (permissoes.contains(permissao)) {
			permissoes.remove(permissao);
		} else {
			permissoes.add(permissao);
		}
		return null;
	}
	
	public List<Usuario> getLista() {
		if (this.lista == null) {			
			this.lista = usuarioRN.listar();
		}
		return this.lista;
	}
	
	public String cancelaEdicao(){
		return "/publico/login";
	}
	
	private String mostraMensagem(String menssagem){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = 
				new FacesMessage(menssagem);
			context.addMessage( null, facesMessage);
			return null;
	}
	
	private Boolean validaSenha(String senha){		 
		if ( !senha.equals( this.confirmarSenha ) ) {
			return false;
		}
		return true;
	}
	
	public String getConfirmarSenha() {
		return confirmarSenha;
	}
	public void setConfirmarSenha(String confirmaSenha) {
		this.confirmarSenha = confirmaSenha;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Usuario getUsuario() {
		return usuario;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}
	
	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}