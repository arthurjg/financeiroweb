package financeiro.rn;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import financeiro.dao.UsuarioDAO;
import financeiro.model.Usuario;
import financeiro.rn.CategoriaRN;
import financeiro.rn.UsuarioRN;

public class UsuarioRNTest {
	
	UsuarioRN usuarioRN;
	UsuarioDAO usuarioDAOMock;
	CategoriaRN categoriaRnMock;

	@Before
	public void setUp() throws Exception {		
		usuarioDAOMock = mock(UsuarioDAO.class);
		categoriaRnMock = mock(CategoriaRN.class);
		//TODO REFATORAR
		//usuarioRN = new UsuarioRN(usuarioDAOMock, categoriaRnMock);
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
		
		when(usuarioDAOMock.carregar(codigo)).thenReturn(usuarioMock);			
		
		//executando acao
		usuario = usuarioRN.carregar(codigo);			
		
		//validando saida
		Assert.assertEquals(usuario.getCodigo(), usuarioMock.getCodigo());	
		Assert.assertEquals(usuario.getLogin(), usuarioMock.getLogin());
	}

	@Test
	public void testBuscarPorLogin() {
		//montando cenario
		Usuario usuarioMock = new Usuario();		
		Usuario usuario = new Usuario();
		String login = "Arthur";
		int codigo = 9999;		
		usuarioMock.setLogin(login);
		usuarioMock.setCodigo(codigo);	
				
		when(usuarioDAOMock.buscarPorLogin(login)).thenReturn(usuarioMock);			
				
		//executando acao
		usuario = usuarioRN.buscarPorLogin(login);			
				
		//validando saida
		Assert.assertEquals(usuario.getCodigo(), usuarioMock.getCodigo());	
		Assert.assertEquals(usuario.getLogin(), usuarioMock.getLogin());
	}

	@Test
	public void testBuscarPorEmail() {
		//montando cenario
		Usuario usuarioMock = new Usuario();		
		Usuario usuario = new Usuario();
		String login = "Arthur";
		String email = "arthur@gmail.com";
		int codigo = 9999;		
		usuarioMock.setLogin(login);
		usuarioMock.setCodigo(codigo);	
		usuarioMock.setEmail(email);
			
		when(usuarioDAOMock.buscarPorEmail(email)).thenReturn(usuarioMock);			
				
		//executando acao
		usuario = usuarioRN.buscarPorEmail(email);			
			
		//validando saida
		Assert.assertEquals(usuario.getCodigo(), usuarioMock.getCodigo());	
		Assert.assertEquals(usuario.getLogin(), usuarioMock.getLogin());
		Assert.assertEquals(usuario.getEmail(), usuarioMock.getEmail());
	}

	@Test
	public void testSalvar() {
		Usuario usuario = new Usuario();		
		String login = "Arthur";
		usuario.setLogin(login);		
		
		usuarioRN.salvar(usuario);
		//TODO
		//Assert.assertTrue(usuario.getPermissao().contains("ROLE_USUARIO"));
		verify(usuarioDAOMock).salvar(usuario);	
		verify(categoriaRnMock).salvaEstruturaPadrao(usuario);
	}

	@Test
	public void testExcluir() {
		Usuario usuario = new Usuario();		
		String login = "Arthur";
		usuario.setLogin(login);		
		
		usuarioRN.excluir(usuario);
				
		verify(usuarioDAOMock).excluir(usuario);	
		verify(categoriaRnMock).excluir(usuario);
	}

	@Test
	public void testListar() {	
		
		//montando cenario
		Usuario usuarioA = new Usuario();		
		Usuario usuarioB = new Usuario();
		String login = "Arthur";
		int codigo = 9999;		
		usuarioA.setLogin(login);
		usuarioB.setCodigo(codigo);	
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuarioA);
		usuarios.add(usuarioB);
				
		when(usuarioDAOMock.listar()).thenReturn(usuarios);			
				
		//executando acao
		List<Usuario> usuarioTest = usuarioRN.listar();			
				
		//validando saida
		Assert.assertEquals(usuarios.size(), usuarioTest.size());	
		Assert.assertEquals(usuarios.get(0).getLogin(), usuarioTest.get(0).getLogin());
		Assert.assertEquals(usuarios.get(1).getCodigo(), usuarioTest.get(1).getCodigo());		
	}

}
