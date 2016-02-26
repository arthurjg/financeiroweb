package finnanceiro.rn;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import financeiro.dao.CategoriaDAO;
import financeiro.dao.UsuarioDAO;
import financeiro.dao.mock.UsuarioDAOMock;
import financeiro.model.Usuario;
import financeiro.rn.UsuarioRN;

public class UsuarioRNTest {
	
	UsuarioRN usuarioRN;

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testCarregar() {
		
		//montando cenario
		Usuario usuarioMock = new Usuario();		
		Usuario usuario = new Usuario();
		String login = "Arthur";
		int codigo = 9999;		
		usuarioMock.setLogin(login);
		usuarioMock.setCodigo(codigo);		
		
		CategoriaDAO categoriaDaoMock = mock(CategoriaDAO.class);
		UsuarioDAO usuarioDAOMock = mock(UsuarioDAO.class);
		when(usuarioDAOMock.carregar(codigo)).thenReturn(usuarioMock);
		usuarioRN = new UsuarioRN(usuarioDAOMock, categoriaDaoMock);	
		
		//executando acao
		usuario = usuarioRN.carregar(codigo);			
		
		//validando saida
		Assert.assertEquals(usuario.getCodigo(), usuarioMock.getCodigo());	
		Assert.assertEquals(usuario.getLogin(), usuarioMock.getLogin());
	}

	@Test
	public void testBuscarPorLogin() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuscarPorEmail() {
		fail("Not yet implemented");
	}

	@Test
	public void testSalvar() {
		Usuario usuario = new Usuario();
		Usuario usuarioMock = new Usuario();
		String login = "Arthur";
		usuario.setLogin(login);
		usuarioMock.setLogin(login);
		usuarioRN.salvar(usuario);
		usuario = usuarioRN.buscarPorLogin(login);
		Assert.assertEquals(usuario.getLogin(), usuarioMock.getLogin());		
	}

	@Test
	public void testExcluir() {
		fail("Not yet implemented");
	}

	@Test
	public void testListar() {
		fail("Not yet implemented");
	}

}
