package financeiro.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import financeiro.dao.UsuarioDAO;
import financeiro.model.Permissao;
import financeiro.model.Usuario;
import financeiro.util.UtilException;

public class UsuarioDAOJDBC implements UsuarioDAO {
	
	private Connection connection;
	
	public UsuarioDAOJDBC(){
		try {
			connection = ConnectionFactory.getDSConnection();
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void iniciaTransacao() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encerraTransacao() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void salvar(Usuario usuario) {
		String sql = "insert into usuario "
				+ "(nome,login,email,senha,ativo) values (?,?,?,?,?)";

		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getLogin());
			stmt.setString(3, usuario.getEmail());
			stmt.setString(4, usuario.getSenha());
			stmt.setBoolean(5, true);

			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}			
		}
		
	}

	@Override
	public void atualizar(Usuario usuario) {
		String sql = "update usuario set nome=?, email=?, login=?, senha=? where id=?";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getLogin());			
			stmt.setString(4, usuario.getSenha());
			stmt.execute();
			stmt.close();
			System.out.println("\nAlteração realizada com sucesso.\n");
		} catch (SQLException e) {
			System.out.println("\nAlteração não realizada.\n");
			throw new RuntimeException(e);			
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}			
		}
		
	}

	@Override
	public void excluir(Usuario usuario) {
		try {
			PreparedStatement stmt = 
				connection.prepareStatement("delete from usuario where id=?");
			stmt.setLong(1, usuario.getCodigo());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}			
		}
		
	}

	@Override
	public Usuario carregar(Integer codigo) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from Usuario where codigo = ?");
			stmt.setInt(1, codigo);
			ResultSet rs = stmt.executeQuery();
			Usuario usuario = new Usuario();

			while (rs.next()) {
				// criando o objeto Usuario
				usuario.setCodigo(rs.getInt("codigo"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setLogin(rs.getString("login"));
				usuario.setIdioma(rs.getString("idioma"));

				// montando a data através do Calendar
				Date data;
				data = rs.getDate("nascimento");
				usuario.setNascimento(data);
			}

			rs.close();
			stmt.close();
			
			usuario.setPermissoes(this.carregarPermissoes(usuario));
			return usuario;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}			
		}
	}

	@Override
	public Usuario buscarPorLogin(String login) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuario where login = ?");	
			stmt.setString(1, login);
			ResultSet rs = stmt.executeQuery();
			Usuario usuario = new Usuario();

			while (rs.next()) {
				// criando o objeto Usuario
				usuario.setCodigo(rs.getInt("codigo"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setLogin(rs.getString("login"));
				usuario.setIdioma(rs.getString("idioma"));

				// montando a data através do Calendar
				Date data;
				data = rs.getDate("nascimento");
				usuario.setNascimento(data);
			}

			rs.close();
			stmt.close();
			usuario.setPermissoes(this.carregarPermissoes(usuario));
			return usuario;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}			
		}
	}

	@Override
	public Usuario buscarPorEmail(String email) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from Usuario where email = ?");
			ResultSet rs = stmt.executeQuery();
			stmt.setString(1, email);
			Usuario usuario = new Usuario();

			while (rs.next()) {
				// criando o objeto Usuario
				usuario.setCodigo(rs.getInt("codigo"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setLogin(rs.getString("login"));
				usuario.setIdioma(rs.getString("idioma"));

				// montando a data através do Calendar
				Date data;
				data = rs.getDate("nascimento");
				usuario.setNascimento(data);
			}

			rs.close();
			stmt.close();
			usuario.setPermissoes(this.carregarPermissoes(usuario));
			return usuario;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}			
		}
	}

	@Override
	public List<Usuario> listar() {
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuarios");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Usuario
				Usuario usuario = new Usuario();
				usuario.setCodigo(rs.getInt("codigo"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setLogin(rs.getString("login"));
				usuario.setIdioma(rs.getString("idioma"));

				// montando a data através do Calendar
				Date data;
				data = rs.getDate("nascimento");
				usuario.setNascimento(data);

				// adicionando o objeto à lista
				usuarios.add(usuario);
			}

			rs.close();
			stmt.close();
			return usuarios;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}			
		}
	}
	
	private List<Permissao> carregarPermissoes(Usuario usuario){
		try {
			List<Permissao> permissoes = new ArrayList<Permissao>();
			String sql = "SELECT * FROM permissao p	where p.id IN " +
				"(select permissoes_id from usuario_permissao where usuario_codigo = ?);";
			PreparedStatement stmt = 
					this.connection.prepareStatement(sql);
			stmt.setInt(1, usuario.getCodigo());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Usuario
				Permissao permissao = new Permissao();
				permissao.setId(rs.getInt("id"));
				permissao.setNome(rs.getString("nome"));			

				// adicionando o objeto à lista
				permissoes.add(permissao);
			}

			rs.close();
			stmt.close();
			return permissoes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}			
		}
	}

}
