package financeiro.dao.hibernate;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import financeiro.dao.UsuarioDAO;
import financeiro.model.Usuario;
import financeiro.model.databuilder.UsuarioDataBuilder;
import financeiro.test.util.DAOFactory;

public class UsuarioDAOHibernateTest {
	
	UsuarioDAO usuarioDAO;

	@Before
	public void setUp() throws Exception {
		usuarioDAO = DAOFactory.criarUsuarioDAO();
		String login = "johnsmith";
		Usuario usuario = buscarUsuarioPorLogin(login);
		if(usuario != null){
			excluirUsuario(usuario);
		}
	}

	@Test
	public void testSalvarEBuscarPorLogin() {
		
		//montando cenario
		String login = "johnsmith";
		String nome = "John_Smith";
		Usuario usuario = 
				new UsuarioDataBuilder().setLogin(login).setNome(nome).getUsuario();
		Usuario usuarioTeste;	
		
		//executando acao		
		salvarUsuario(usuario);
		
		//validando saida
		usuarioDAO.iniciaTransacao();
		usuarioTeste = usuarioDAO.buscarPorLogin(login);
		usuarioDAO.encerraTransacao();
		Assert.assertNotNull(usuarioTeste);
		Assert.assertEquals(usuario.getLogin(), usuarioTeste.getLogin());
		Assert.assertEquals(usuario.getNome(), usuarioTeste.getNome());
		
		excluirUsuario(usuarioTeste);
	}

	@Test
	public void testAtualizar() {
		// montando cenario
		String login = "johnsmith";
		String nome = "John_Smith";
		Usuario usuario = 
				new UsuarioDataBuilder().setLogin(login).setNome(nome).getUsuario();
		usuario.getPermissao().add("ROLE_USUARIO");
		Usuario usuarioTeste;
		salvarUsuario(usuario);
		usuarioDAO.iniciaTransacao();
		usuario = usuarioDAO.buscarPorLogin(login);		
		
		usuario.setNome("John S");
		usuario.setAtivo(true);

		// executando acao		
		usuarioDAO.atualizar(usuario);
		usuarioDAO.encerraTransacao();

		// validando saida		
		usuarioTeste = buscarUsuarioPorLogin(login);		
		Assert.assertNotNull(usuarioTeste);
		Assert.assertTrue(usuarioTeste.isAtivo());
		Assert.assertEquals(usuario.getNome(), usuarioTeste.getNome());

		excluirUsuario(usuarioTeste);
	}
	
	@Test
	public void testAtualizarSemPermissao() {
		// montando cenario
		String login = "johnsmith";
		String nome = "John_Smith";
		Usuario usuario = 
				new UsuarioDataBuilder().setLogin(login).setNome(nome).getUsuario();
		
		Usuario usuarioTeste;
		salvarUsuario(usuario);
		usuarioDAO.iniciaTransacao();
		usuario = usuarioDAO.buscarPorLogin(login);		
		
		usuario.setNome("John S");
		usuario.setAtivo(true);

		// executando acao		
		usuarioDAO.atualizar(usuario);
		usuarioDAO.encerraTransacao();

		// validando saida		
		usuarioTeste = buscarUsuarioPorLogin(login);		
		Assert.assertNotNull(usuarioTeste);
		Assert.assertTrue(usuarioTeste.isAtivo());
		Assert.assertEquals(usuario.getNome(), usuarioTeste.getNome());

		excluirUsuario(usuarioTeste);
	}

	@Test
	public void testExcluir() {
		// montando cenario
		String login = "johnsmith";
		String nome = "John_Smith";
		Usuario usuario = 
				new UsuarioDataBuilder().setLogin(login).setNome(nome).getUsuario();
		Usuario usuarioTeste;
		salvarUsuario(usuario);		
		usuarioTeste = buscarUsuarioPorLogin(login);		
		Assert.assertNotNull(usuarioTeste);
		Assert.assertEquals(usuario.getLogin(), usuarioTeste.getLogin());		
		
		// executando acao
		excluirUsuario(usuarioTeste);

		// validando saida
		usuarioTeste = buscarUsuarioPorLogin(login);
		Assert.assertNull(usuarioTeste);		
	}

	@Test
	public void testCarregar() {
		// montando cenario
		String login = "johnsmith";
		String nome = "John_Smith";
		Usuario usuario = 
				new UsuarioDataBuilder().setLogin(login).setNome(nome).getUsuario();
		Usuario usuarioTeste;
		salvarUsuario(usuario);

		// executando acao
		usuarioDAO.iniciaTransacao();
		usuario = usuarioDAO.buscarPorLogin(login);
		usuarioTeste = usuarioDAO.carregar(usuario.getCodigo());
		usuarioDAO.encerraTransacao();

		// validando saida		
		Assert.assertNotNull(usuarioTeste);
		Assert.assertEquals(usuario.getCodigo()	, usuarioTeste.getCodigo());
		Assert.assertEquals(usuario.getLogin(), usuarioTeste.getLogin());
		Assert.assertEquals(usuario.getNome(), usuarioTeste.getNome());

		excluirUsuario(usuarioTeste);
	}	

	@Test
	public void testListar() {
		
		// montando cenario
		String login = "johnsmith";
		String email = "john_smith@gmail.com";
		String login2 = "johnshimtd";
		String email2 = "john_shimtd@gmail.com";
		Usuario usuario = new UsuarioDataBuilder().setLogin(login)
				.setEmail(email).getUsuario();
		Usuario usuario2 = new UsuarioDataBuilder().setLogin(login2)
				.setEmail(email2).getUsuario();

		salvarUsuario(usuario);
		salvarUsuario(usuario2);

		// executando acao
		usuarioDAO.iniciaTransacao();
		List<Usuario> usuarios = usuarioDAO.listar();
		usuarioDAO.encerraTransacao();

		// validando saida
		Assert.assertNotNull(usuarios);
		Assert.assertEquals(2, usuarios.size());
		Assert.assertEquals(usuarios.get(0).getEmail(), usuario.getEmail());
		Assert.assertEquals(usuarios.get(1).getLogin(), usuario2.getLogin());

		excluirUsuario(usuarios.get(0));
		excluirUsuario(usuarios.get(1));		
		
	}

	@Test
	public void testBuscaPorEmail() {
		// montando cenario
		String login = "johnsmith";
		String email = "john_smith@gmail.com";
		Usuario usuario = 
				new UsuarioDataBuilder().setLogin(login).setEmail(email).getUsuario();
		Usuario usuarioTeste;
		
		salvarUsuario(usuario);

		// executando acao
		usuarioDAO.iniciaTransacao();
		usuarioTeste = usuarioDAO.buscarPorEmail(email);
		usuarioDAO.encerraTransacao();

		// validando saida		
		Assert.assertNotNull(usuarioTeste);
		Assert.assertEquals(usuario.getLogin(), usuarioTeste.getLogin());
		Assert.assertEquals(usuario.getEmail(), usuarioTeste.getEmail());

		excluirUsuario(usuarioTeste);
	}
	
	private void salvarUsuario(Usuario usuario){
		usuarioDAO.iniciaTransacao();
		usuarioDAO.salvar(usuario);
		usuarioDAO.encerraTransacao();
	}
	
	private void excluirUsuario(Usuario usuario){
		usuarioDAO.iniciaTransacao();
		usuarioDAO.excluir(usuario);
		usuarioDAO.encerraTransacao();
	}
	
	private Usuario buscarUsuarioPorLogin(String login){
		usuarioDAO.iniciaTransacao();
		Usuario usuario = usuarioDAO.buscarPorLogin(login);
		usuarioDAO.encerraTransacao();
		return usuario;
	}

}
