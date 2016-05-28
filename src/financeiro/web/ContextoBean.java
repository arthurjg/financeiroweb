package financeiro.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import financeiro.model.Conta;
import financeiro.model.Usuario;
import financeiro.rn.ContaRN;
import financeiro.rn.UsuarioRN;

@ManagedBean(name = "contextoBean")
@SessionScoped
public class ContextoBean implements Serializable {	
	
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRN usuarioRN;

	@Inject
	private ContaRN contaRN;

	private Usuario	   usuarioLogado	= null;
	private Conta	      contaAtiva	  = null;
	private Locale	      localizacao	  = null;
	private List<Locale>	idiomas;

	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();		
		String login = external.getRemoteUser();

		if(login != null){
			if (this.usuarioLogado == null || !login.equals(this.usuarioLogado.getLogin())) {			
				this.usuarioLogado = usuarioRN.buscarPorLogin(login);
				this.contaAtiva = null;

				String[] info = this.usuarioLogado.getIdioma().split("_");
				Locale locale = new Locale(info[0], info[1]);
				context.getViewRoot().setLocale(locale);			
			}
		}
		
		return usuarioLogado;
	}

	public Conta getContaAtiva() {
		
		if (this.contaAtiva == null) {
			Usuario usuario = this.getUsuarioLogado();			
			this.contaAtiva = contaRN.buscarFavorita(usuario);

			if (this.contaAtiva == null) {
				List<Conta> contas = contaRN.listar(usuario);
				if (contas != null) {
					for (Conta conta : contas) {
						this.contaAtiva = conta;
						break;
					}
				}
			}
		}
		return this.contaAtiva;
	}
	
	public Boolean usuarioTemPermissao(String permissao) {
		Usuario usuario = this.getUsuarioLogado();
		if(usuario != null){
			for( String permissaoUsuario : usuario.getPermissao()){
				if(permissao.equals(permissaoUsuario)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void getResponse(HttpServletResponse res, HttpServletRequest req){
		res.getStatus();
		Enumeration<String> attrs = req.getAttributeNames();
 		System.out.println(attrs);
	}
	
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		HttpSession session = (HttpSession) external.getSession(false);
		session.invalidate();	
		return "/publico/login";
	}

	public void setContaAtiva(ValueChangeEvent event) {
		Integer conta = (Integer) event.getNewValue();		
		this.contaAtiva = contaRN.carregar(conta);
	}

	public Locale getLocaleUsuario() {
		if (this.localizacao == null) {
			Usuario usuario = this.getUsuarioLogado();
			String idioma = usuario.getIdioma();
			String[] info = idioma.split("_");
			this.localizacao = new Locale(info[0], info[1]);
		}
		return this.localizacao;
	}

	public List<Locale> getIdiomas() {
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<Locale> locales = context.getApplication().getSupportedLocales();
		this.idiomas = new ArrayList<Locale>();
		while (locales.hasNext()) {
			this.idiomas.add(locales.next());
		}
		return idiomas;
	}

	public void setIdiomaUsuario(String idioma) {
		
		this.usuarioLogado = usuarioRN.carregar(this.getUsuarioLogado().getCodigo());
		this.usuarioLogado.setIdioma(idioma);
		usuarioRN.salvar(this.usuarioLogado);

		String[] info = idioma.split("_");
		Locale locale = new Locale(info[0], info[1]);

		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(locale);
	}

	public void setUsuarioLogado(Usuario usuario) {
		this.usuarioLogado = usuario;
	}
	
	public UsuarioRN getUsuarioRN() {
		return usuarioRN;
	}

	public void setUsuarioRN(UsuarioRN usuarioRN) {
		this.usuarioRN = usuarioRN;
	}

	public ContaRN getContaRN() {
		return contaRN;
	}

	public void setContaRN(ContaRN contaRN) {
		this.contaRN = contaRN;
	}
}

