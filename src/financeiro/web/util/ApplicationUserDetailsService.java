package financeiro.web.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import financeiro.dao.repository.UsuarioRepository;
import financeiro.model.Usuario;
import financeiro.util.EntityManagerFactorySingleton;

public class ApplicationUserDetailsService implements UserDetailsService {
	
	@Inject
	private UsuarioRepository usuarioRepo;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
		
		UsuarioPermissao usuarioPermissao = null;
		
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		EntityManager manager = emf.createEntityManager();
		
		usuarioRepo = new UsuarioRepository();
		usuarioRepo.setManager(manager);
		
		if(usuarioRepo == null){
			System.out.println("e null*************");
		}
		
		Usuario usuario = usuarioRepo.buscarPorLogin(login);
		
		if(usuario != null){
			usuarioPermissao = new UsuarioPermissao(usuario, getPermissoes(usuario));
		} else {
			throw new UsernameNotFoundException("Usuario não encontrado - login não existe.");
		}		
		return usuarioPermissao;
	}
	
	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(String permissao : usuario.getPermissao()){
			authorities.add(new GrantedAuthorityImpl(permissao));
		}		
		
		return authorities;
	}

}
