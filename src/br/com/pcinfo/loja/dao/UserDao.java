package br.com.pcinfo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.pcinfo.loja.models.SystemUser;

public class UserDao implements UserDetailsService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String jpql = "select u from SystemUser u where u.login = :login";
		List<SystemUser> users = em.createQuery(jpql, SystemUser.class).setParameter("login", username).getResultList();
		if (users.isEmpty()) {
			throw new UsernameNotFoundException("O usuário" + " não existe");
		}
		return users.get(0);

	}

}
