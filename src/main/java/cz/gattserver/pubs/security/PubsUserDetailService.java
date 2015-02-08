package cz.gattserver.pubs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import cz.gattserver.pubs.facades.UserFacade;
import cz.gattserver.pubs.model.dto.UserDTO;

@Component
public class PubsUserDetailService implements UserDetailsService {

	@Autowired
	private UserFacade userFacade;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		final UserDTO user = userFacade.getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException("Unable to find user");
		}

		return user;
	}

}
