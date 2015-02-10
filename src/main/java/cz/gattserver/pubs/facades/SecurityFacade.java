package cz.gattserver.pubs.facades;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import cz.gattserver.pubs.model.dto.UserDTO;

public interface SecurityFacade extends Serializable {

	public boolean login(String username, String password);

	public UserDTO getCurrentUser();

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException;
}
