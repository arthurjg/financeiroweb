package financeiro.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import financeiro.conta.Conta;
import financeiro.conta.ContaRN;
import financeiro.usuario.Usuario;
import financeiro.usuario.UsuarioRN;

@ManagedBean(name = "contextoBean")
@SessionScoped
public class ContextoBean {

	private Usuario	   usuarioLogado	= null;
	private Conta	      contaAtiva	= null;

	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String loginEmail = external.getRemoteUser();

		if (this.usuarioLogado == null || !loginEmail.equals(this.usuarioLogado.getEmail())) {
	
			if (loginEmail != null) {
				UsuarioRN usuarioRN = new UsuarioRN();
				this.usuarioLogado = usuarioRN.buscarPorEmail(loginEmail);
				this.contaAtiva = null;
			}
		}
		
		return this.usuarioLogado;
	}

	public Conta getContaAtiva() {
		if (this.contaAtiva == null) {
			Usuario usuario = this.getUsuarioLogado();

			ContaRN contaRN = new ContaRN();
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

	public void setContaAtiva(ValueChangeEvent event) {

		Integer conta = (Integer) event.getNewValue();

		ContaRN contaRN = new ContaRN();
		this.contaAtiva = contaRN.carregar(conta);
	}

	public void setUsuarioLogado(Usuario usuario) {
		this.usuarioLogado = usuario;
	}
}

