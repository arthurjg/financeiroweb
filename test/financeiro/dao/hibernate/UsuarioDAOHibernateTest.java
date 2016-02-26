package financeiro.dao.hibernate;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import financeiro.dao.UsuarioDAO;
import financeiro.model.Usuario;
import financeiro.test.util.DAOFactory;


public class UsuarioDAOHibernateTest {
	
	UsuarioDAO usuarioDAO;

	@Before
	public void setUp() throws Exception {
		usuarioDAO = DAOFactory.criarUsuarioDAO();
	}

	@Test
	public void testSalvar() {
		Usuario usuario = new Usuario();
		Usuario usuarioMock = new Usuario();
		String login = "Arthur";
		usuario.setLogin(login);
		usuarioMock.setLogin(login);
		usuarioDAO.salvar(usuario);
		usuario = usuarioDAO.buscaPorLogin(login);
		Assert.assertEquals(usuario.getLogin(), usuarioMock.getLogin());
	}

	@Test
	public void testAtualizar() {
		fail("Not yet implemented");
	}

	@Test
	public void testExcluir() {
		fail("Not yet implemented");
	}

	@Test
	public void testCarregar() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuscaPorLogin() {
		fail("Not yet implemented");
	}

	@Test
	public void testListar() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuscaPorEmail() {
		fail("Not yet implemented");
	}

}
