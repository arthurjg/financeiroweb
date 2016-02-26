package financeiro.web;


import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import financeiro.model.Conta;
import financeiro.model.Usuario;
import financeiro.rn.ContaRN;
import financeiro.web.util.ContextoUtil;

@ManagedBean(name = "contaBean")
@RequestScoped
public class ContaBean {

	private Conta				selecionada	= new Conta();
	private List<Conta>		lista			= null;

	public void salvar() {
		
		ContextoBean contextoBean = ContextoUtil.getContextoBean();		
		Usuario usuariologado = contextoBean.getUsuarioLogado();
		this.selecionada.setUsuario( usuariologado );
		ContaRN contaRN = new ContaRN();
		contaRN.salvar(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;		
		
	}

	public void excluir() { //1
		ContaRN contaRN = new ContaRN();
		contaRN.excluir(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;
	}

	public void tornarFavorita() { //3
		ContaRN contaRN = new ContaRN();
		contaRN.tornarFavorita(this.selecionada);
		this.selecionada = new Conta();
	}
	
	public List<Conta> getLista() { //2
		if (this.lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();

			ContaRN contaRN = new ContaRN();
			this.lista = contaRN.listar(contextoBean.getUsuarioLogado());
		}
		return this.lista;
	}	

	public Conta getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Conta selecionada) {
		this.selecionada = selecionada;
	}

	

}

