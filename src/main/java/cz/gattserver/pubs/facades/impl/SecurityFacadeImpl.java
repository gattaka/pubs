package cz.gattserver.pubs.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.gattserver.pubs.facades.SecurityFacade;
import cz.gattserver.pubs.facades.UserFacade;
import cz.gattserver.pubs.model.dao.UserRepository;
import cz.gattserver.pubs.model.dto.UserDTO;

@Transactional
@Component
public class SecurityFacadeImpl implements SecurityFacade {

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private UserRepository userRepository;

	public boolean login(String username, String password) {
		UserDTO loggedUser = userFacade.getUserByLogin(username, password);
		if (loggedUser == null)
			return false;

		Authentication authentication = new UsernamePasswordAuthenticationToken(loggedUser, loggedUser.getPassword(),
				loggedUser.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return true;
	}

	public UserDTO getCurrentUser() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDTO)
			return (UserDTO) principal;
		else
			return null;
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		if (!(authentication.getPrincipal() instanceof String)
				|| (!(authentication.getCredentials() instanceof String)))
			throw new AuthenticationException("Authentication failed") {
				private static final long serialVersionUID = 1622317305057326834L;
			};

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		UserDTO loggedUser = userFacade.getUserByLogin(username, password);
		if (loggedUser == null)
			throw new BadCredentialsException("Bad credentials");

		authentication.setAuthenticated(true);
		return authentication;
	}
}
